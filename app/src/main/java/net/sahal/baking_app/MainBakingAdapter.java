package net.sahal.baking_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.models.BakingList;

import java.util.ArrayList;

public class MainBakingAdapter extends RecyclerView.Adapter<MainBakingAdapter.RecyclerViewHolder> {

    private MainBakingAdapter.RecyclerViewHolder myHolder = null;
    private ArrayList<BakingList> List;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;

        public RecyclerViewHolder(View view) {
            super(view);

            mCardView = itemView.findViewById(R.id.card_container);
            mTextView = itemView.findViewById(R.id.text_holder);
        }
    }

    public MainBakingAdapter(ArrayList<BakingList> List) {
        this.List = List;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        this.myHolder = holder;
        BakingList baking = List.get(position);
        holder.mTextView.setText(baking.getName());
    }

    @Override
    public int getItemCount() {
        return List.size();
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
