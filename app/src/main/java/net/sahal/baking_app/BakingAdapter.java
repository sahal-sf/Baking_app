package net.sahal.baking_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.RecyclerViewHolder> {

    private BakingAdapter.RecyclerViewHolder myHolder = null;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;

        public RecyclerViewHolder(View view) {
            super(view);

            mCardView = itemView.findViewById(R.id.card_container);
            mTextView = itemView.findViewById(R.id.text_holder);
        }
    }

    public BakingAdapter() {
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        this.myHolder = holder;
    }

    @Override
    public int getItemCount() {
        return 5;
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
