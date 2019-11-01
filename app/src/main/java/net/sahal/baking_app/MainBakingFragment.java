package net.sahal.baking_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainBakingFragment extends Fragment {

    private RecyclerView rView;

    public static Fragment newInstance() {
        return new MainBakingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        rView = view.findViewById(R.id.Recycler_Fragment);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(new MainBakingAdapter(MainActivity.List));
        rView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Fragment fragment = IngredientStepsFragment.newInstance(position);
                getFragmentManager().beginTransaction()
                        .replace(R.id.Fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        return view;
    }
}
