package com.fullertonfinnovatica.Transaction;

import com.fullertonfinnovatica.SignUpModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TransactionAPIs {

    String BASE_URL = "http://192.168.86.1:3002/api/";
    String BASE_URL2 = "http://192.168.86.1:3003/api/";
    String BASE_URL3 = "http://192.168.86.1:3004/api/";
    String BASE_URL4 = "http://192.168.86.1:3005/api/";

    @Headers("Content-Type: application/json")
    @POST("trans")
    Call<InventoryModel> sendInventory(@Body String body);

}
