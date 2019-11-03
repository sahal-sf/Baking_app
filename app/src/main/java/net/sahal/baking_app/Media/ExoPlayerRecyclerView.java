//package net.sahal.baking_app.Media;
//
//import android.content.Context;
//import android.graphics.Point;
//import android.net.Uri;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.Display;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.RequestManager;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.PlaybackParameters;
//import com.google.android.exoplayer2.Player;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.source.ExtractorMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.TrackGroupArray;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
//import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
//
//import net.sahal.baking_app.Adapter.StepsAdapter;
//import net.sahal.baking_app.R;
//import net.sahal.baking_app.models.Steps;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class ExoPlayerRecyclerView extends RecyclerView {
//
//    private ImageView mediaCoverImage, volumeControl;
//    private ProgressBar progressBar;
//    private View viewHolderParent;
//    private FrameLayout mediaContainer;
//    private PlayerView videoSurfaceView;
//    private SimpleExoPlayer videoPlayer;
//
//    private Steps step;
//    private Context context;
//    private boolean isVideoViewAdded;
//    private RequestManager requestManager;
//
//    private VolumeState volumeState;
//    private View.OnClickListener videoViewClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            toggleVolume();
//        }
//    };
//
//    public ExoPlayerRecyclerView(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public ExoPlayerRecyclerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    private void init(Context context) {
//        this.context = context.getApplicationContext();
//
//        videoSurfaceView = new PlayerView(this.context);
//        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
//
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
//        videoSurfaceView.setUseController(false);
//        videoSurfaceView.setPlayer(videoPlayer);
//        setVolumeControl(VolumeState.ON);
//        playVideo();
//        videoPlayer.addListener(new Player.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//            }
//
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                switch (playbackState) {
//
//                    case Player.STATE_BUFFERING:
//                        if (progressBar != null) {
//                            progressBar.setVisibility(VISIBLE);
//                        }
//
//                        break;
//                    case Player.STATE_ENDED:
//                        videoPlayer.seekTo(0);
//                        break;
//                    case Player.STATE_IDLE:
//
//                        break;
//                    case Player.STATE_READY:
//                        if (progressBar != null) {
//                            progressBar.setVisibility(GONE);
//                        }
//                        if (!isVideoViewAdded) {
//                            addVideoView();
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onRepeatModeChanged(int repeatMode) {
//            }
//
//            @Override
//            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//            }
//
//            @Override
//            public void onPositionDiscontinuity(int reason) {
//            }
//
//            @Override
//            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//            }
//
//            @Override
//            public void onSeekProcessed() {
//            }
//        });
//    }
//
//    public void playVideo() {
//
//        StepsAdapter.RecyclerMediaViewHolder holder = (StepsAdapter.RecyclerMediaViewHolder) getTag();
//
//        mediaCoverImage = holder.mediaCoverImage;
//        progressBar = holder.progressBar;
//        volumeControl = holder.volumeControl;
//        viewHolderParent = holder.itemView;
//        requestManager = holder.requestManager;
//        mediaContainer = holder.mediaContainer;
//
//        videoSurfaceView.setPlayer(videoPlayer);
//        viewHolderParent.setOnClickListener(videoViewClickListener);
//
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, ""));
//        String mediaUrl = step.getVideoURL();
//        if (mediaUrl != null) {
//            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mediaUrl));
//            videoPlayer.prepare(videoSource);
//            videoPlayer.setPlayWhenReady(true);
//        }
//    }
//
//    private void removeVideoView(PlayerView videoView) {
//        ViewGroup parent = (ViewGroup) videoView.getParent();
//        if (parent == null) {
//            return;
//        }
//
//        int index = parent.indexOfChild(videoView);
//        if (index >= 0) {
//            parent.removeViewAt(index);
//            isVideoViewAdded = false;
//            viewHolderParent.setOnClickListener(null);
//        }
//    }
//
//    private void addVideoView() {
//        mediaContainer.addView(videoSurfaceView);
//        isVideoViewAdded = true;
//        videoSurfaceView.requestFocus();
//        videoSurfaceView.setVisibility(VISIBLE);
//        videoSurfaceView.setAlpha(1);
//        mediaCoverImage.setVisibility(GONE);
//    }
//
//    private void resetVideoView() {
//        if (isVideoViewAdded) {
//            removeVideoView(videoSurfaceView);
//            videoSurfaceView.setVisibility(INVISIBLE);
//            mediaCoverImage.setVisibility(VISIBLE);
//        }
//    }
//
//    public void releasePlayer() {
//
//        if (videoPlayer != null) {
//            videoPlayer.release();
//            videoPlayer = null;
//        }
//        viewHolderParent = null;
//    }
//
//    public void onPausePlayer() {
//        if (videoPlayer != null) {
//            videoPlayer.stop(true);
//        }
//    }
//
//    private void toggleVolume() {
//        if (videoPlayer != null) {
//            if (volumeState == VolumeState.OFF) {
//                setVolumeControl(VolumeState.ON);
//            } else if (volumeState == VolumeState.ON) {
//                setVolumeControl(VolumeState.OFF);
//            }
//        }
//    }
//
//    private void setVolumeControl(VolumeState state) {
//        volumeState = state;
//        if (state == VolumeState.OFF) {
//            videoPlayer.setVolume(0f);
//            animateVolumeControl();
//        } else if (state == VolumeState.ON) {
//            videoPlayer.setVolume(1f);
//            animateVolumeControl();
//        }
//    }
//
//    private void animateVolumeControl() {
//        if (volumeControl != null) {
//            volumeControl.bringToFront();
//            if (volumeState == VolumeState.OFF) {
//                requestManager.load(R.drawable.ic_volume_off).into(volumeControl);
//            } else if (volumeState == VolumeState.ON) {
//                requestManager.load(R.drawable.ic_volume_on).into(volumeControl);
//            }
//            volumeControl.animate().cancel();
//            volumeControl.setAlpha(1f);
//            volumeControl.animate().alpha(0f).setDuration(600).setStartDelay(1000);
//        }
//    }
//
//    public void setMediaObjects(Steps step) {
//        this.step = step;
//    }
//
//    private enum VolumeState {
//        ON, OFF
//    }
//}
//
