package net.sahal.baking_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import net.sahal.baking_app.MainActivity;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.Steps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Steps> steps;
    private int stepPosition;
    private RequestManager requestManager;


    public class RecyclerMediaViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout mediaContainer;
        public ImageView mediaCoverImage, volumeControl;
        public ProgressBar progressBar;
        public RequestManager requestManager;
        private View parent;

        public RecyclerMediaViewHolder(View view) {
            super(view);

            parent = itemView;
            mediaContainer = itemView.findViewById(R.id.mediaContainer);
            mediaCoverImage = itemView.findViewById(R.id.ivMediaCoverImage);
            progressBar = itemView.findViewById(R.id.progressBar);
            volumeControl = itemView.findViewById(R.id.ivVolumeControl);
        }

        void onBind(Steps step, RequestManager requestManager) {
            this.requestManager = requestManager;
            parent.setTag(this);
            this.requestManager.load(step.getVideoURL()).into(mediaCoverImage);
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

    public StepsAdapter(int MainPosition, int stepPosition, RequestManager requestManager) {
        this.steps = MainActivity.List.get(MainPosition).getSteps();
        this.stepPosition = stepPosition;
        this.requestManager = requestManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_card_view, parent, false);
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
                RecyclerMediaViewHolder mediaView = (RecyclerMediaViewHolder) holder;
                mediaView.onBind(steps.get(stepPosition), requestManager);
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

}
