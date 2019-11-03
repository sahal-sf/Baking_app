package net.sahal.baking_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.models.Steps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.RecyclerViewHolder> {

    private StepsAdapter.RecyclerViewHolder myHolder = null;
    private ArrayList<Steps> steps;

    public class RecyclerTextViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;

        public RecyclerTextViewHolder(View view) {
            super(view);

            mCardView = itemView.findViewById(R.id.card_container);
            mTextView = itemView.findViewById(R.id.text_holder);
        }
    }

    public class RecyclerMediaViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;

        public RecyclerMediaViewHolder(View view) {
            super(view);

            mCardView = itemView.findViewById(R.id.card_container);
            mTextView = itemView.findViewById(R.id.text_holder);
        }
    }

    public StepsAdapter(int position) {
        this.steps = MainActivity.List.get(position).getSteps();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        this.myHolder = holder;
        if(position == 0){
            holder.mTextView.setText("Ingredients");
        }else{
            Steps step = steps.get(position - 1);
            holder.mTextView.setText(step.getShortDescription());
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

    public int getAdapterPosition() {
        return myHolder != null ? myHolder.getAdapterPosition() : 0;
    }
}
