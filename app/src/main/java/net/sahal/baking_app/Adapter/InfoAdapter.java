package net.sahal.baking_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.R;
import net.sahal.baking_app.models.Steps;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.RecyclerViewHolder> {

    private ArrayList<Steps> steps;
    public InfoAdapter.RecyclerViewHolder myHolder = null;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public RecyclerViewHolder(View view) {
            super(view);
            mTextView = itemView.findViewById(R.id.text_holder);
        }
    }

    public InfoAdapter(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_info_card_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        this.myHolder = holder;
        if (position == 0) {
            holder.mTextView.setText("Ingredients");
        } else {
            holder.mTextView.setText((position - 1) + " - " + steps.get(position - 1).getShortDescription());
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
