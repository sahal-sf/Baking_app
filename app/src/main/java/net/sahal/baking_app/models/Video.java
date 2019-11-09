package net.sahal.baking_app.models;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import net.sahal.baking_app.Adapter.StepsAdapter;
import net.sahal.baking_app.R;

public class Video {

    private String url;
    private AppCompatActivity activity;
    private static SimpleExoPlayer player;
    private PlayerView playerView;
    private ImageView fullscreenButton;
    private boolean fullscreen = false;

    public Video(String url, AppCompatActivity activity) {
        this.url = url;
        this.activity = activity;
    }

    public void setPlayer(SimpleExoPlayer player, final StepsAdapter.RecyclerMediaViewHolder holder) {
        this.player = player;
        this.playerView = holder.mExoPlayer;


        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.ic_fullscreen_open));
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if (activity.getSupportActionBar() != null) {
                        activity.getSupportActionBar().show();
                    }
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    holder.layout.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    holder.layout.getLayoutParams().height = (int) (200 * activity.getResources().getDisplayMetrics().density);

                    RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    Params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    Params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    Params.setMargins(16, 16, 16, 16);
                    playerView.setLayoutParams(Params);

                    fullscreen = false;

                } else {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.ic_fullscreen_close));
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if (activity.getSupportActionBar() != null) {
                        activity.getSupportActionBar().hide();
                    }
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    holder.layout.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    holder.layout.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;

                    RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    Params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    Params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    Params.setMargins(0, 0, 0, 0);
                    playerView.setLayoutParams(Params);

                    fullscreen = true;
                }
            }
        });
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return activity.getApplicationContext();
    }

    public static void Release() {
        if (player != null) {
            player.release();
        }
    }
}
