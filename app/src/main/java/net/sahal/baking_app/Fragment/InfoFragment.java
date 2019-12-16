package net.sahal.baking_app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.Activity.InfoDetailActivity;
import net.sahal.baking_app.Adapter.RecyclerItemClickListener;
import net.sahal.baking_app.Adapter.InfoAdapter;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.BakingList;

public class InfoFragment extends Fragment {

    private BakingList recipe;
    private boolean mTwoPane;
    private int position;

    private RecyclerView rView;
    private InfoAdapter infoAdapter;
    private final String INFO_POSITION = "Info_position";
    private final String INFO_RECIPE = "Info_Recipe";
    private final String INFO_TWOPANE = "Info_TwoPane";

    public static Fragment newInstance(BakingList recipe, boolean mTwoPane) {
        return new InfoFragment(recipe, mTwoPane);
    }

    public InfoFragment(BakingList recipe, boolean mTwoPane) {
        this.recipe = recipe;
        this.mTwoPane = mTwoPane;
    }

    public InfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info_recycler_view, container, false);

        if(savedInstanceState != null){
            recipe = (BakingList) savedInstanceState.getSerializable(INFO_RECIPE);
            mTwoPane = savedInstanceState.getBoolean(INFO_TWOPANE);
            position = savedInstanceState.getInt(INFO_POSITION);
        }

        getActivity().setTitle(recipe.getName());

        rView = view.findViewById(R.id.info_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rView.setLayoutManager(layoutManager);
        rView.setHasFixedSize(true);

        infoAdapter = new InfoAdapter(recipe.getSteps());
        rView.setAdapter(infoAdapter);

        if (savedInstanceState != null) {
            rView.post(new Runnable() {
                @Override
                public void run() {
                    rView.scrollToPosition(position);
                }
            });
        }

        rView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mTwoPane) {
                    Fragment infoDetailFragment = InfoDetailFragment.newInstance(recipe, position, mTwoPane);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.info_detail_fragment, infoDetailFragment)
                            .commit();
                } else {
                    Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                    intent.putExtra("Recipe", recipe);
                    intent.putExtra("Position", position);
                    intent.putExtra("TwoPane", mTwoPane);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INFO_POSITION, infoAdapter.getAdapterPosition());
        outState.putSerializable(INFO_RECIPE, recipe);
        outState.putBoolean(INFO_TWOPANE, mTwoPane);

        super.onSaveInstanceState(outState);
    }
}