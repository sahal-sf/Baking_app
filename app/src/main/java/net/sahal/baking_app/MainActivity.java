package net.sahal.baking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends SingleFragment {


    @Override
    protected Fragment createFragment() {
        return new RecclerFragment().newInstance();
    }
}
