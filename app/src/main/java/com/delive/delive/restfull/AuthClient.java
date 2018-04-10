package com.delive.delive.restfull;

import com.delive.delive.model.LoginInformation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Jorge Simeão on 26/03/2018.
 */

public interface AuthClient {

    @POST("auth")
    Call<com.delive.delive.model.Auth> login(@Body LoginInformation loginInformation);
}
