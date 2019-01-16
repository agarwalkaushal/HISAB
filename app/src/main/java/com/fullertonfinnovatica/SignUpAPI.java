package com.fullertonfinnovatica;

import com.fullertonfinnovatica.Inventory.InventoryModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignUpAPI {

    String BASE_URL = "http://192.168.1.100:3001/api/";

    @Headers("Content-Type: application/json")
    @POST("details")
    Call<SignUpModel> getUse(@Body String body);

}
