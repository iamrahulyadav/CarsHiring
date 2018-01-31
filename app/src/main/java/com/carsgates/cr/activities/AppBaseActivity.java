package com.carsgates.cr.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.carsgates.cr.Utils.LocaleHelper;
import com.carsgates.cr.interfaces.IBaseActivity;
import com.carsgates.cr.interfaces.IRefreshToken;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Atul Kumar Gupta on 5/3/2017.
 * Contact Number : +91 8470967433
 */

public class AppBaseActivity extends AppCompatActivity implements IBaseActivity,IRefreshToken{
    int CALLBACK ;
    ActionBar actionBar ;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getToken(final IRefreshToken iRefreshToken){
        final TinyDB sharedpref=new TinyDB(getApplicationContext());
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal();
        String grant_type = "client_credentials";
        String client_id = "developer";
        String client_secret = "5a633cf4392e8";

        Call<ApiResponse> apiResponseCall = retroFitApis.token(grant_type, client_id, client_secret);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                String token = response.body().access_token;
                sharedpref.putString("access_token",token);
                iRefreshToken.refreshTokenCallBack();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(AppBaseActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void refreshTokenCallBack() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    public void setLanguage() {

    }
}
