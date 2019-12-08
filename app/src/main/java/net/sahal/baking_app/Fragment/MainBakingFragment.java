package net.sahal.baking_app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.Adapter.MainBakingAdapter;
import net.sahal.baking_app.MainActivity;
import net.sahal.baking_app.R;
import net.sahal.baking_app.Adapter.RecyclerItemClickListener;


public class MainBakingFragment extends Fragment {

    private RecyclerView rView;
    private AppCompatActivity Activity;

    public static Fragment newInstance(AppCompatActivity Activity) {
        return new MainBakingFragment(Activity);
    }

    public MainBakingFragment(AppCompatActivity Activity){
        this.Activity = Activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);


        rView = view.findViewById(R.id.Recycler_Fragment);

        if (view.findViewById(R.id.Fragment_container_2) == null) {
            rView.setLayoutManager(new LinearLayoutManager(getActivity()));

        }else{
            rView.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
            rView.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
            rView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        }

        rView.setAdapter(new MainBakingAdapter(MainActivity.List));
        rView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Fragment fragment = IngredientStepsFragment.newInstance(position, Activity);
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
