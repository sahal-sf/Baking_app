package net.sahal.baking_app;

import androidx.fragment.app.Fragment;

import net.sahal.baking_app.Fragment.MainBakingFragment;
import net.sahal.baking_app.Fragment.MainFragment;
import net.sahal.baking_app.models.BakingList;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends MainFragment {

    public static ArrayList<BakingList> List;

    @Override
    protected Fragment createFragment() {
        readJson("baking.json");
        return new MainBakingFragment(this).newInstance(this);
    }

    public void readJson(String jsonFile) {
        try {
            InputStream input = getAssets().open(jsonFile);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            JSONArray jsonArray = new JSONArray(new String(buffer));
            this.List = new ArrayList<>();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    List.add(new BakingList(this, jsonArray.getJSONObject(i)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
