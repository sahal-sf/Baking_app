package net.sahal.baking_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerFragment extends Fragment {

    private RecyclerView rView;

    public static Fragment newInstance() {
        return new RecyclerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        rView = view.findViewById(R.id.Recycler_Fragment);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rView.setAdapter(new BakingAdapter());
        rView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Hi and Welcome", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

        return view;
    }
}
