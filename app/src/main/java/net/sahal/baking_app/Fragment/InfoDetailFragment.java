package net.sahal.baking_app.Fragment;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import net.sahal.baking_app.Adapter.IngredientsAdapter;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.BakingList;
import net.sahal.baking_app.models.Steps;

public class InfoDetailFragment extends Fragment {

    private BakingList recipe;
    private boolean mTwoPane;
    private int index;
    private SimpleExoPlayer mExoPlayer;
    private boolean playWhenReady = true;
    private long position = -1;
    private int listPosition = 0;
    private boolean fullscreen = false;

    private RecyclerView rView;
    private IngredientsAdapter ingredientsAdapter;

    private final String INFO_DETAIL_LIST_POSITION = "Info_Detail_List_position";
    private final String INFO_DETAIL_PLAYER_POSITION = "Info_Detail_Player_position";
    private final String INFO_DETAIL_RECIPE = "Info_Detail_Recipe";
    private final String INFO_DETAIL_TWOPANE = "Info_Detail_TwoPane";
    private final String INFO_DETAIL_INDEX = "Info_Detail_Index";
    private final String INFO_DETAIL_PLAY_WHEN_READY = "Info_Detail_Play_When_Ready";

    public static Fragment newInstance(BakingList recipe, int index, boolean mTwoPane) {
        return new InfoDetailFragment(recipe, index, mTwoPane);
    }

    public InfoDetailFragment(BakingList recipe, int index, boolean mTwoPane) {
        this.recipe = recipe;
        this.index = index;
        this.mTwoPane = mTwoPane;
    }

    public InfoDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipe = (BakingList) savedInstanceState.getSerializable(INFO_DETAIL_RECIPE);
            index = savedInstanceState.getInt(INFO_DETAIL_INDEX);
            mTwoPane = savedInstanceState.getBoolean(INFO_DETAIL_TWOPANE);

            if (index == 0) {
                listPosition = savedInstanceState.getInt(INFO_DETAIL_LIST_POSITION);
            } else {
                position = savedInstanceState.getLong(INFO_DETAIL_PLAYER_POSITION, C.TIME_UNSET);
                playWhenReady = savedInstanceState.getBoolean(INFO_DETAIL_PLAY_WHEN_READY);
            }
        }

        if (index == 0) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Introduction");
            return inflater.inflate(R.layout.activity_info_recycler_view, container, false);

        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Step " + (index - 1));
            return inflater.inflate(R.layout.activity_info_detail_step_view, container, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (index == 0) {
            rView = getView().findViewById(R.id.info_recycler_view);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
            rView.setLayoutManager(layoutManager);
            ingredientsAdapter = new IngredientsAdapter(recipe.getIngredients());
            rView.setAdapter(ingredientsAdapter);
            rView.post(new Runnable() {
                @Override
                public void run() {
                    rView.scrollToPosition(listPosition);
                }
            });

        } else {
            Steps step = recipe.getSteps().get(index - 1);

            TextView ShortDescription = getView().findViewById(R.id.step_short_description);
            ShortDescription.setText(step.getShortDescription());

            TextView Description = getView().findViewById(R.id.step_description);
            Description.setText(step.getDescription());

            if (!step.getVideoURL().isEmpty()) {
                initializerPlayer(Uri.parse(step.getVideoURL()));
            }

            if (!step.getThumbnailURL().isEmpty()) {
                ImageView Thumbnail = getView().findViewById(R.id.step_thumbnail);
                Thumbnail.setVisibility(View.VISIBLE);
                Picasso.with(getContext())
                        .load(step.getThumbnailURL())
                        .into(Thumbnail);
            }

            Navigation(step);
        }
    }

    private void Navigation(Steps step) {
        if (mTwoPane) {
            Button mPrevious = getView().findViewById(R.id.navigation_previous);
            Button mNext = getView().findViewById(R.id.navigation_next);

            if (step.getId() == 0) {
                mNext.setVisibility(View.VISIBLE);
                mPrevious.setVisibility(View.INVISIBLE);

            } else if (step.getId() == recipe.getSteps().size() - 1) {
                mNext.setVisibility(View.INVISIBLE);
                mPrevious.setVisibility(View.VISIBLE);

            } else {
                mNext.setVisibility(View.VISIBLE);
                mPrevious.setVisibility(View.VISIBLE);
            }
            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.info_detail_fragment, InfoDetailFragment.newInstance(recipe, index + 1, mTwoPane))
                            .commit();
                }
            });
            mPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.info_detail_fragment, InfoDetailFragment.newInstance(recipe, index - 1, mTwoPane))
                            .commit();
                }
            });
        } else {
            RelativeLayout navigationLayout = getView().findViewById(R.id.navigation_layout);
            navigationLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void initializerPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            SimpleExoPlayerView player = getView().findViewById(R.id.step_player);
            player.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            player.setPlayer(mExoPlayer);

            player.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position > 0)
                mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playWhenReady);

            fullScreen(player);
        }
    }

    public void fullScreen(final SimpleExoPlayerView player) {
        final ImageView fullscreenButton = player.findViewById(R.id.exo_fullscreen_icon);
        final FragmentActivity thisActivity = getActivity();

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(thisActivity, R.drawable.ic_fullscreen_open));
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if (((AppCompatActivity) thisActivity).getSupportActionBar() != null) {
                        ((AppCompatActivity) thisActivity).getSupportActionBar().show();
                    }

                    if(mTwoPane){
                        thisActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }else{
                        thisActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }

                    AspectRatioFrameLayout.LayoutParams Params = (AspectRatioFrameLayout.LayoutParams) player.getLayoutParams();
                    Params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    Params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    Params.setMargins(16, 16, 16, 16);
                    player.setLayoutParams(Params);

                    fullscreen = false;

                } else {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(thisActivity.getApplicationContext(), R.drawable.ic_fullscreen_close));
                    thisActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if (((AppCompatActivity) thisActivity).getSupportActionBar() != null) {
                        ((AppCompatActivity) thisActivity).getSupportActionBar().hide();
                    }
                    thisActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    AspectRatioFrameLayout.LayoutParams Params = (AspectRatioFrameLayout.LayoutParams) player.getLayoutParams();
                    Params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    Params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    Params.setMargins(0, 0, 0, 0);
                    player.setLayoutParams(Params);

                    fullscreen = true;
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(INFO_DETAIL_RECIPE, recipe);
        outState.putInt(INFO_DETAIL_INDEX, index);
        outState.putBoolean(INFO_DETAIL_TWOPANE, mTwoPane);
        if (index == 0) {
            outState.putLong(INFO_DETAIL_LIST_POSITION, ingredientsAdapter.getAdapterPosition());
        } else {
            outState.putLong(INFO_DETAIL_PLAYER_POSITION, position);
            outState.putBoolean(INFO_DETAIL_PLAY_WHEN_READY, playWhenReady);
        }
        super.onSaveInstanceState(outState);
    }


}
