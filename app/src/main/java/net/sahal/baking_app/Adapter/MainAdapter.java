package net.sahal.baking_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.sahal.baking_app.R;
import net.sahal.baking_app.models.BakingList;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewHolder> {

    private List<BakingList> List;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private Context mContext;

        public RecyclerViewHolder(View view) {
            super(view);

            mImageView = itemView.findViewById(R.id.image_holder);
            mTextView = itemView.findViewById(R.id.text_holder);
            mContext = itemView.getContext();
        }
    }

    public MainAdapter(List<BakingList> List) {
        this.List = List;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_card_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        BakingList baking = List.get(position);

        holder.mTextView.setText(baking.getName());
        if (!baking.getImage().isEmpty()) {
            Picasso.with(holder.mContext)
                    .load(baking.getImage())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.mImageView);
        }
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
}
