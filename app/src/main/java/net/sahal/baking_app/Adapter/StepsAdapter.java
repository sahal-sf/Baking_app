package net.sahal.baking_app.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import net.sahal.baking_app.Fragment.StepsFragment;
import net.sahal.baking_app.MainActivity;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.Steps;
import net.sahal.baking_app.models.Video;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Steps> steps;
    private int stepPosition;
    private int MainPosition;
    private Fragment mainFragment;
    private int size;

    public class RecyclerMediaViewHolder extends RecyclerView.ViewHolder {

        public PlayerView mExoPlayer;
        public ProgressBar bar;
        public RelativeLayout layout;
        public TextView textView;

        public RecyclerMediaViewHolder(View itemView) {
            super(itemView);
            mExoPlayer = itemView.findViewById(R.id.player);
            bar = itemView.findViewById(R.id.spinnerVideoDetails);
            layout = itemView.findViewById(R.id.layout);
            textView = itemView.findViewById(R.id.text_holder);
        }
    }

    public class RecyclerTextViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;

        public RecyclerTextViewHolder(View view) {
            super(view);

            mCardView = view.findViewById(R.id.card_container);
            mTextView = view.findViewById(R.id.text_holder);
        }
    }

    public class RecyclerNavigationViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private Button mPrevious;
        private Button mNext;

        public RecyclerNavigationViewHolder(View view) {
            super(view);

            mCardView = view.findViewById(R.id.card_container);
            mPrevious = view.findViewById(R.id.previous);
            mNext = view.findViewById(R.id.next);
        }
    }

    public StepsAdapter(int MainPosition, int stepPosition, Fragment fragment, int size) {
        this.steps = MainActivity.List.get(MainPosition).getSteps();
        this.stepPosition = stepPosition;
        this.MainPosition = MainPosition;
        this.mainFragment = fragment;
        this.size = size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new RecyclerMediaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.media_card_view, parent, false));

        } else if (viewType == 1) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new RecyclerTextViewHolder(itemView);

        } else if (viewType == 2) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_card_view, parent, false);
            return new RecyclerNavigationViewHolder(itemView);

        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                initPlayer(holder);
                break;

            case 1:
                RecyclerTextViewHolder textView = (RecyclerTextViewHolder) holder;
                textView.mTextView.setText(steps.get(stepPosition).getDescription());
                break;

            case 2:
                Navigation((RecyclerNavigationViewHolder) holder);
                break;
        }
    }

    private void Navigation(RecyclerNavigationViewHolder navigationView) {
        if (stepPosition + 1 >= steps.size()) {
            navigationView.mNext.setVisibility(View.INVISIBLE);
            navigationView.mPrevious.setVisibility(View.VISIBLE);

        } else if (stepPosition - 1 < 0) {
            navigationView.mNext.setVisibility(View.VISIBLE);
            navigationView.mPrevious.setVisibility(View.INVISIBLE);
        } else {
            navigationView.mNext.setVisibility(View.VISIBLE);
            navigationView.mPrevious.setVisibility(View.VISIBLE);
        }
        navigationView.mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepPosition + 1 < steps.size()) {
                    Video.Release();
                    Fragment fragment = StepsFragment.newInstance(MainPosition, (stepPosition + 1),size);
                    mainFragment.getFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();

                }
            }
        });
        navigationView.mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepPosition - 1 <= 0) {
                    Video.Release();
                    Fragment fragment = StepsFragment.newInstance(MainPosition, (stepPosition - 1), size);
                    mainFragment.getFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();

                }
            }
        });
    }

    private void initPlayer(final RecyclerView.ViewHolder holder) {
        final StepsAdapter.RecyclerMediaViewHolder mediaHolder = (StepsAdapter.RecyclerMediaViewHolder) holder;

        if (!steps.get(stepPosition).getVideo().getUrl().isEmpty()) {

            mediaHolder.textView.setVisibility(View.GONE);

            steps.get(stepPosition).getVideo().setPlayer(ExoPlayerFactory.newSimpleInstance(steps.get(stepPosition).getVideo().getContext()), mediaHolder);

            mediaHolder.mExoPlayer.setPlayer(steps.get(stepPosition).getVideo().getPlayer());
            mediaHolder.mExoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    steps.get(stepPosition).getVideo().getContext(), Util.getUserAgent(steps.get(stepPosition).getVideo().getContext(),
                    steps.get(stepPosition).getVideo().getContext().getString(R.string.app_name)));
            MediaSource videoSource = new ProgressiveMediaSource.Factory(
                    dataSourceFactory).createMediaSource(Uri.parse(steps.get(stepPosition).getVideo().getUrl()));

            steps.get(stepPosition).getVideo().getPlayer().prepare(videoSource, true, true);
            steps.get(stepPosition).getVideo().getPlayer().setPlayWhenReady(true);
            steps.get(stepPosition).getVideo().getPlayer().seekTo(0);
            steps.get(stepPosition).getVideo().getPlayer().addListener(new Player.EventListener() {
                @Override
                public void onLoadingChanged(boolean isLoading) {
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    switch (playbackState) {
                        case Player.STATE_READY:
                            mediaHolder.bar.setVisibility(View.GONE);
                            steps.get(stepPosition).getVideo().getPlayer().setPlayWhenReady(true);
                            break;
                        case Player.STATE_BUFFERING:
                            mediaHolder.bar.setVisibility(View.VISIBLE);
                            steps.get(stepPosition).getVideo().getPlayer().seekTo(0);
                            break;
                        case Player.STATE_IDLE:
                            steps.get(stepPosition).getVideo().getPlayer().retry();
                            break;
                    }
                }
            });
        } else {
            mediaHolder.bar.setVisibility(View.GONE);
            mediaHolder.mExoPlayer.setVisibility(View.GONE);
            mediaHolder.textView.setText("Sorry No Video");
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
