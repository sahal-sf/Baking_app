package net.sahal.baking_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.R;
import net.sahal.baking_app.models.Ingredients;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.RecyclerIngredientViewHolder> {

    private ArrayList<Ingredients> ingredient;
    public IngredientsAdapter.RecyclerIngredientViewHolder myHolder = null;

    public class RecyclerIngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView quantityView;
        public TextView measureView;
        public TextView ingredientView;

        public RecyclerIngredientViewHolder(View itemView) {
            super(itemView);
            quantityView = itemView.findViewById(R.id.quantityView);
            measureView = itemView.findViewById(R.id.measureView);
            ingredientView = itemView.findViewById(R.id.ingredientView);
        }
    }

    public IngredientsAdapter(ArrayList<Ingredients> ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public RecyclerIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_info_detail_ingr_view, parent, false);
        return new RecyclerIngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerIngredientViewHolder holder, int position) {
        this.myHolder = holder;
        holder.quantityView.setText(ingredient.get(position).getQuantity() + "");
        holder.measureView.setText(ingredient.get(position).getMeasure());
        holder.ingredientView.setText(ingredient.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredient.size();
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