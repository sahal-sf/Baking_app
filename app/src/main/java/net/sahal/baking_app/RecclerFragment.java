package net.sahal.baking_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecclerFragment extends Fragment {

    public static Fragment newInstance(){
        return new RecclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        RecyclerView RV = view.findViewById(R.id.Recycler_Fragment);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public CardView mcardView;
        private TextView textView;
        
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder (LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.card_view, container, false));
        }
    }
}
