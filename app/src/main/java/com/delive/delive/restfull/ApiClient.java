package com.delive.delive.restfull;

import com.delive.delive.model.Auth;
import com.delive.delive.model.LoginInformation;
import com.delive.delive.model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Jorge Simeão on 03/08/2017.
 */

public interface ApiClient {

    String BASE_URL = "http://192.168.15.190:3000/";
    Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    @POST("auth")
    Call<Auth> login(@Body LoginInformation loginInformation);

    @GET("users")
    Call<User> getUsers();

    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);

    @POST("users")
    Call<User> createUser(@Body User user);
}
