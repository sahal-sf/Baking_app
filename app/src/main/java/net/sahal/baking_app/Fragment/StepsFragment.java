package net.sahal.baking_app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.R;
import net.sahal.baking_app.Adapter.RecyclerItemClickListener;
import net.sahal.baking_app.Adapter.StepsAdapter;

public class StepsFragment extends Fragment {

    private RecyclerView rView;
    private int MainPosition;
    private int stepPosition;
    private int size;

    public static Fragment newInstance(int MainPosition, int stepPosition, int size) {
        return new StepsFragment(MainPosition, stepPosition, size);

    }

    public StepsFragment(int MainPosition, int stepPosition, int size) {
        this.MainPosition = MainPosition;
        this.stepPosition = stepPosition;
        this.size = size;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment_all, container, false);

        rView = view.findViewById(R.id.Recycler_Fragment);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(new StepsAdapter(MainPosition, stepPosition, this, size));
        return view;
    }
}
