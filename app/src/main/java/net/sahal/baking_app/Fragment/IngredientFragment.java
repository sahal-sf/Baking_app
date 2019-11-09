package net.sahal.baking_app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.Adapter.IngredientAdapter;
import net.sahal.baking_app.R;

public class IngredientFragment extends Fragment {

    private RecyclerView rView;
    private int MainPosition;

    public static Fragment newInstance(int MainPosition) {
        return new IngredientFragment(MainPosition);
    }

    public IngredientFragment(int MainPosition) {
        this.MainPosition = MainPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        rView = view.findViewById(R.id.Recycler_Fragment);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(new IngredientAdapter(MainPosition));
        return view;
    }
}
