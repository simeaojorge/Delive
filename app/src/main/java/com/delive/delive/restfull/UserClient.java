package com.delive.delive.restfull;

import com.delive.delive.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Jorge Simeão on 26/03/2018.
 */

public interface UserClient {

    @GET("users")
    Call<com.delive.delive.model.User> getUsers();

    @GET("users/{user}")
    Call<com.delive.delive.model.User> getUser(@Path("user") String user);

    @GET("users/phone_number/{phoneNumber}")
    Call<com.delive.delive.model.User> getUserByPhone(@Path("user/phone_number") String phoneNumber);

    @POST("users")
    Call<com.delive.delive.model.User> createUser(@Body User user);

    @PUT("users/{id}")
    Call<com.delive.delive.model.User> updateUser(@Path("id") String id, @Body User user);
}
