package com.delive.delive.restfull;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jorge Simeão on 03/08/2017.
 */

public class AuthInterceptor implements Interceptor {

    Context context;

    @Override
    public Response intercept(Chain chain)
            throws IOException {

        SharedPreferences prefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        Request request = chain.request();
        if(prefs!=null && prefs.contains("access_token")){//essentially checking if the prefs has a non null token
            request = request.newBuilder()
                    .addHeader("Authenticator", prefs.getString("access_token", null))
                    .build();
        }
        Response response = chain.proceed(request);
        return response;
    }
}

public class ApiClient {

//    private static String BASE_URL = "http://ec2-52-67-36-43.sa-east-1.compute.amazonaws.com:3000";
    private static String BASE_URL = "http://192.168.1.115:3000";

    private static Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addInterceptor(new AuthInterceptor())
                    .build();

    public static <S> S create(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }

}
