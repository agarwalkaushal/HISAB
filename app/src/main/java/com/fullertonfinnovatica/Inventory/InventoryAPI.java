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

    String BASE_URL = "https://nexus-account.herokuapp.com/";

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginModel> login(@Field("id") String id, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("inventory/add")
    Call<InventoryModel> postInventory(@Header("authorization") String header, @Field("name") String invName, @Field("category") String invCategory, @Field("quantity") int invQty, @Field("cost") int invCost, @Field("expiry") String expiry, @Field("thresholdQuantity") String thrhld);

    @FormUrlEncoded
    @POST("inventory/update")
    Call<JsonObject> updateInventory(@Header("authorization") String header, @Field("name") String invName, @Field("category") String invCategory, @Field("usedQuantity") int invQty);

    @GET("inventory")
    Call<JsonObject> getInventoryy(@Header("authorization") String header);

}
