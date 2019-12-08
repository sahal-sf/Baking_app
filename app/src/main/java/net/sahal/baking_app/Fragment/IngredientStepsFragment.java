package net.sahal.baking_app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.Adapter.IngredientStepsAdapter;
import net.sahal.baking_app.R;
import net.sahal.baking_app.Adapter.RecyclerItemClickListener;
import net.sahal.baking_app.models.Video;

public class IngredientStepsFragment extends Fragment {

    private RecyclerView rView;
    private int id;
    private AppCompatActivity Activity;

    public static Fragment newInstance(int id, AppCompatActivity Activity) {
        return new IngredientStepsFragment(id, Activity);
    }

    public IngredientStepsFragment(int id, AppCompatActivity Activity) {
        this.id = id;
        this.Activity = Activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        rView = view.findViewById(R.id.Recycler_Fragment);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(new IngredientStepsAdapter(id));

        if (view.findViewById(R.id.Fragment_container_2) == null) {
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
                        Fragment fragment = StepsFragment.newInstance(id, position, 3);
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

        } else {
            rView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Video.Release();
                    if (position == 0) {

                        FragmentManager fragmentManager = Activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.Fragment_container_2,
                                IngredientFragment.newInstance(id)).addToBackStack(null).commit();
                    } else {
                        position -= 1;

                        FragmentManager fragmentManager = Activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.Fragment_container_2,
                                StepsFragment.newInstance(id, position, 2)).addToBackStack(null).commit();
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {
                }
            }));
        }
        return view;
    }
}

