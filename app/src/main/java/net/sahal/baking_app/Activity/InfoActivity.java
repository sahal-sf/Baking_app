package net.sahal.baking_app.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.sahal.baking_app.Fragment.InfoFragment;
import net.sahal.baking_app.R;
import net.sahal.baking_app.models.BakingList;

public class InfoActivity extends AppCompatActivity {

    private static final String INFO_FRAGMENT = "info_fragment";
    private Fragment infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        boolean mTwoPane = false;
        if (findViewById(R.id.info_detail_fragment) != null) {
            mTwoPane = true;
        }

        Intent intent = getIntent();
        if (intent != null) {
            FragmentManager manager = getSupportFragmentManager();

            if (savedInstanceState != null) {
                infoFragment = manager.getFragment(savedInstanceState, INFO_FRAGMENT);

            } else {
                infoFragment = InfoFragment.newInstance((BakingList) intent.getSerializableExtra("Recipe"), mTwoPane);
                manager.beginTransaction().add(R.id.info_fragment, infoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, INFO_FRAGMENT, infoFragment);
    }
}
