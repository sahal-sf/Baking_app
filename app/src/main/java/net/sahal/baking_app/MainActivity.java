package net.sahal.baking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;

import net.sahal.baking_app.Models.BakingList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends SingleFragment {


    @Override
    protected Fragment createFragment() {
        return new RecclerFragment().newInstance();
    }

    public void getJSON(String bufferString) {
//        JSONArray jsonArray = new JSONArray(bufferString);
//        JSONObject anObject = jsonArray.getJSONObject("");
//        BakingList aBook = new BakingList();
//        aBook.setTitle((String) anObject.get("title"));
    }

    public String loadJSONFromAsset(Context context) {
        try {
            InputStream is = context.getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
