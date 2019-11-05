package net.sahal.baking_app.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import net.sahal.baking_app.MainActivity;
import net.sahal.baking_app.Media.VideoPlayerConfig;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.Steps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Player.EventListener {

    private ArrayList<Steps> steps;
    private int stepPosition;
    private RecyclerMediaViewHolder MediaHolder;
    private Context context;
    public static SimpleExoPlayer player;

    public class RecyclerMediaViewHolder extends RecyclerView.ViewHolder {

        private PlayerView videoFullScreenPlayer;
        private ProgressBar spinnerVideoDetails;

        public RecyclerMediaViewHolder(View view) {
            super(view);
            videoFullScreenPlayer = view.findViewById(R.id.videoFullScreenPlayer);
            spinnerVideoDetails = view.findViewById(R.id.spinnerVideoDetails);
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

    public StepsAdapter(int MainPosition, int stepPosition) {
        this.steps = MainActivity.List.get(MainPosition).getSteps();
        this.stepPosition = stepPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_card_view, parent, false);
            context = parent.getContext();
            return new RecyclerMediaViewHolder(itemView);

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
                MediaHolder = (RecyclerMediaViewHolder) holder;
                if (player == null) {
                    LoadControl loadControl = new DefaultLoadControl(
                            new DefaultAllocator(true, 16),
                            VideoPlayerConfig.MIN_BUFFER_DURATION,
                            VideoPlayerConfig.MAX_BUFFER_DURATION,
                            VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                            VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);

                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                    TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
                    player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context), trackSelector, loadControl);
                    MediaHolder.videoFullScreenPlayer.setPlayer(player);
                }
                if (steps.get(stepPosition).getVideoURL() != null) {
                    DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "hi"), bandwidthMeter);
                    MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(steps.get(stepPosition).getVideoURL()));
                    LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
                    player.prepare(loopingSource);
                    player.setPlayWhenReady(true);
                    player.addListener(this);
                }
                break;

            case 1:
                RecyclerTextViewHolder textView = (RecyclerTextViewHolder) holder;
                textView.mTextView.setText(steps.get(stepPosition).getDescription());
                break;

            case 2:
                RecyclerNavigationViewHolder navigationView = (RecyclerNavigationViewHolder) holder;
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
                break;
        }
    }

    public static void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
            System.out.println("gdlfjahsdljfhasdfa-dfa-sdf-a-sdf-----asdfasd-f-asd-fasdfasdfasdfasdfasdfasdfasdfasdfa");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                MediaHolder.spinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_READY:
                MediaHolder.spinnerVideoDetails.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
