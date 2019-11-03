package net.sahal.baking_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import net.sahal.baking_app.models.Steps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Steps> steps;


    public class RecyclerMediaViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private SimpleExoPlayerView playerView;

        public RecyclerMediaViewHolder(View view) {
            super(view);

            mCardView = itemView.findViewById(R.id.card_container);
            playerView = itemView.findViewById(R.id.playerView);
        }
    }

    public class RecyclerTextViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;

        public RecyclerTextViewHolder(View view) {
            super(view);

            mCardView = itemView.findViewById(R.id.card_container);
            mTextView = itemView.findViewById(R.id.text_holder);
        }
    }



    public StepsAdapter(int position) {
        this.steps = MainActivity.List.get(position).getSteps();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_card_view, parent, false);
            return new RecyclerMediaViewHolder(itemView);

        }else if(viewType == 1){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new RecyclerTextViewHolder(itemView);

        }else if(viewType == 2){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new RecyclerTextViewHolder(itemView);

        }else{
            return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){

            case 0:
                RecyclerMediaViewHolder mediaView = (RecyclerMediaViewHolder) holder;
                break;

            case 1:
                RecyclerTextViewHolder textView = (RecyclerTextViewHolder) holder;
                break;

            case 2:
                RecyclerTextViewHolder navigationView = (RecyclerTextViewHolder) holder;
                break;
        }
    }


    @Override
    public int getItemCount() {
        return steps.size() + 1;
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
