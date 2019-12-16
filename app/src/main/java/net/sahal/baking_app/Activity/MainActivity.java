package net.sahal.baking_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sahal.baking_app.Adapter.MainAdapter;
import net.sahal.baking_app.Adapter.RecyclerItemClickListener;
import net.sahal.baking_app.R;
import net.sahal.baking_app.Testing.CheckIdlingResource;
import net.sahal.baking_app.models.API;
import net.sahal.baking_app.models.BakingList;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static List<BakingList> List;

    private Context mainContext;
    private MainAdapter mainAdapter;
    private RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.main_title));
        mainContext = this;
        CheckIdlingResource.setIdleResourceTo(false);

        rView = findViewById(R.id.recyclerview_recipe);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        rView.setLayoutManager(layoutManager);

        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.Base_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API request = retrofit.create(API.class);

        Call<List<BakingList>> call = request.getJSON();
        call.enqueue(new Callback<List<BakingList>>() {
            @Override
            public void onResponse(Call<List<BakingList>> call, Response<List<BakingList>> response) {
                List = response.body();
                updateUI();
                CheckIdlingResource.setIdleResourceTo(true);
            }

            @Override
            public void onFailure(Call<List<BakingList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.api_error, Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
                CheckIdlingResource.setIdleResourceTo(false);
            }
        });
    }

    private void updateUI() {
        mainAdapter = new MainAdapter(List);
        rView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mainContext, InfoActivity.class);
                intent.putExtra("Recipe",List.get(position) );
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        rView.setAdapter(mainAdapter);

    }
}
