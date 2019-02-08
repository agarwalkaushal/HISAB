package com.fullertonfinnovatica.Networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkingAPI {

    //String BASE_URL = "http://192.168.43.8:3000/api/";
    String BASE_URL = "https://api.myjson.com/bins/";

//    @GET("points")
    @GET("t6ky8")
    Call<List<NetworkingModel>> getBirds();

}
