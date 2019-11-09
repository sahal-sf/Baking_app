package net.sahal.baking_app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.Adapter.IngredientStepsAdapter;
import net.sahal.baking_app.R;
import net.sahal.baking_app.Adapter.RecyclerItemClickListener;

public class IngredientStepsFragment extends Fragment {

    private RecyclerView rView;
    private int id;

    public static Fragment newInstance(int id) {
        return new IngredientStepsFragment(id);
    }

    public IngredientStepsFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        rView = view.findViewById(R.id.Recycler_Fragment);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(new IngredientStepsAdapter(id));
        rView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    Fragment fragment = IngredientFragment.newInstance(id);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();

                } else {
                    position -= 1;
                    Fragment fragment = StepsFragment.newInstance(id, position);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        return view;
    }
}

