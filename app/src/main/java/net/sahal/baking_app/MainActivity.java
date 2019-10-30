package net.sahal.baking_app;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends SingleFragment {


    @Override
    protected Fragment createFragment() {
        return new RecyclerFragment().newInstance();
    }
}
