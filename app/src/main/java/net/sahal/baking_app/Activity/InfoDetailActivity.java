package net.sahal.baking_app.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.sahal.baking_app.Fragment.InfoDetailFragment;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.BakingList;

public class InfoDetailActivity extends AppCompatActivity {

    private BakingList recipe;
    private int position;
    private boolean mTwoPane;

    private static final String INFO_DETAIL_FRAGMENT = "info_detail_fragment";
    private Fragment infoDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        Intent intent = getIntent();
        if (intent != null) {
            recipe = (BakingList) intent.getSerializableExtra("Recipe");
            position = intent.getIntExtra("Position", 0);
            mTwoPane = intent.getBooleanExtra("TwoPane", false);
        }

        FragmentManager manager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            infoDetailFragment = manager.getFragment(savedInstanceState, INFO_DETAIL_FRAGMENT);
        } else {
            infoDetailFragment = InfoDetailFragment.newInstance(recipe, position, mTwoPane);
            manager.beginTransaction()
                    .replace(R.id.info_detail_fragment, infoDetailFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, INFO_DETAIL_FRAGMENT, infoDetailFragment);
    }
}
