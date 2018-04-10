package com.delive.delive.restfull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jorge Simeão on 03/08/2017.
 */

public class ApiClient {

//    private static String BASE_URL = "http://ec2-52-67-36-43.sa-east-1.compute.amazonaws.com:3000";
    private static String BASE_URL = "http://192.168.1.115:3000";

    private static Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    public static <S> S create(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }

}
