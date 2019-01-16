package com.fullertonfinnovatica.Inventory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InventoryAPI {

    String BASE_URL = "http://192.168.1.100:3004/api/";

    @Headers("Content-Type: application/json")
    @POST("cash")
    Call<InventoryModel> getUser(@Body String body);

    @GET("cash")
    Call<List<InventoryModel>> getStuff();


}
