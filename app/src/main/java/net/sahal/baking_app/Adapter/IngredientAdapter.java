package net.sahal.baking_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.MainActivity;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.Ingredients;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.RecyclerIngredientViewHolder> {

    private ArrayList<Ingredients> ingredient;

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

    public IngredientAdapter(int MainPosition) {
        this.ingredient = MainActivity.List.get(MainPosition).getIngredients();
    }

    @Override
    public RecyclerIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_view, parent, false);
        return new RecyclerIngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerIngredientViewHolder holder, int position) {
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
}