package com.carsgates.cr.webservices;


import android.content.Context;
import android.widget.Toast;

import com.mukesh.tinydb.TinyDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class GetToken{
    public  static String token;
    static TinyDB sharedpref;
    public static String checktoken(final Context context)
    {
        sharedpref=new TinyDB(context);
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal();
        String grant_type = "client_credentials";
        String id = "testclient";
        String secret = "testpass";
        Call<ApiResponse> apiResponseCall = retroFitApis.token(grant_type, id, secret);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
              token = response.body().access_token;
                Toast.makeText(context, "Connection successfull" + token, Toast.LENGTH_SHORT).show();
                sharedpref.putString("access_token",token);
                }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
                 token="";
            }
        });
        return token;
    }
}
