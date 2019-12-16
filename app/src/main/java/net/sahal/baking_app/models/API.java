package net.sahal.baking_app.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    String Base_URL = "https://d17h27t6h515a5.cloudfront.net";

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<BakingList>> getJSON();
}
