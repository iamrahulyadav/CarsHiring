package com.carsgates.cr.webservices;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Atul Kumar Gupta on 04-11-2016.
 */

public class RetrofitApiBuilder {

    public final static String BASE_URL = "https://carsgates.com/";
    private final static String BASE_WEBSERVICE_URL = BASE_URL + "webservices/";
    public final static String IMG_BASE_URL = BASE_URL+ "upload/";

    public static RetroFitApis getRetrofitGlobal(){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request;
                request = chain.request().newBuilder().
                        addHeader("content-type", "application/x-www-form-urlencoded")
                        .build();

                return chain.proceed(request);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_WEBSERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RetroFitApis.class);
    }

}
