package com.fullertonfinnovatica.Inventory;

import com.fullertonfinnovatica.Accounts.LoginModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InventoryAPI {

    String BASE_URL = "https://test-nexus-inventory.herokuapp.com/api/";

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> login(@Field("id") String id, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("cash")
    Call<InventoryModel> postInventory(@Header("authorization") String header, @Field("inventory_name") String invName, @Field("inventory_category") String invCategory, @Field("inventory_qty") String invQty, @Field("inventory_cost") String invCost);

    @GET("cash")
    Call<JsonArray> getInventoryy(@Header("authorization") String header);

}
