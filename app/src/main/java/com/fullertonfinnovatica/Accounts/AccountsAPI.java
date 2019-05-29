package com.fullertonfinnovatica.Accounts;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AccountsAPI {

    String BASE_URL = "https://nexus-account.herokuapp.com/";

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginModel> login(@Field("id") String id, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("journal/add")
    Call<JournalEntryModel> journalEntry(@Header("authorization") String header, @Field("from") String from, @Field("to") String to, @Field("date") String date, @Field("debit") String debit, @Field("credit") String credit, @Field("narration") String narration);

    @GET("journal")
    Call<JournalEntryListModel> journalRetrieveExp(@Header("authorization") String header);

    @GET("ledger")
    Call<JsonObject> getLedger(@Header("authorization") String header);

    @GET("final/pnl")
    Call<JsonObject> getFinal(@Header("authorization") String header);

    @GET("trial")
    Call<JsonObject> getTrial(@Header("authorization") String header);

}
