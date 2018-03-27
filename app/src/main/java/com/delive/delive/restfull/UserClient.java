package com.delive.delive.restfull;

import com.delive.delive.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Jorge Simeão on 26/03/2018.
 */

public interface UserClient {

    @GET("users")
    public abstract Call<com.delive.delive.model.User> getUsers();

    @GET("users/{user}")
    public abstract Call<com.delive.delive.model.User> getUser(@Path("user") String user);

    @POST("users")
    public abstract Call<com.delive.delive.model.User> createUser(@Body User user);
}
