package com.carsgates.cr.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carsgates.cr.R;
import com.carsgates.cr.Utils.AppGlobal;
import com.carsgates.cr.webservices.AboutUs;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.Data;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Atul Kumar Gupta on 5/3/2017.
 * Contact Number : +91 8470967433
 */

public class AboutUsActivity extends AppBaseActivity {

    private TinyDB sharedpref;
    private String token,language_code ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        sharedpref=new TinyDB(getApplicationContext());
        token=sharedpref.getString("access_token");
        language_code = sharedpref.getString("language_code");
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(getResources().getString(R.string.action_about_us));
        getAboutUsDetails();
    }

    private void getAboutUsDetails() {
        final AboutUsActivity _this  = this ;
        RetroFitApis retroFitApis= RetrofitApiBuilder.getRetrofitGlobal();
        Call<ApiResponse> responseCall=retroFitApis.about_us(token,language_code);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body()!=null){
                    if(response.body().status)
                    {
                        Data data  = response.body().response ;
                        if(data!=null){
                            AboutUs about_us = data.about_us ;
                            String banner  = about_us.cms_banner ;
                            String content  =about_us.cms_language_content ;
                            String title  =about_us.cms_title ;
                            final ImageView ivBannerAboutUs = (ImageView)findViewById(R.id.ivBannerAboutUs);
                            if(banner!=null && !banner.isEmpty()){
                                String imagePath  = RetrofitApiBuilder.IMG_BASE_URL+banner ;
                                Log.d("Image URL",RetrofitApiBuilder.IMG_BASE_URL+banner);
                                Glide.with(getApplicationContext())
                                        .load(imagePath)
                                        .into(ivBannerAboutUs);
                            }
                            final TextView tvContentAboutUs = (TextView)findViewById(R.id.tvContentAboutUs);
                            actionBar.setTitle(title);
                            if (content!=null){
                                content = content.replaceAll("<(.*?)>","");//Removes all items in brackets
                                content = content.replaceAll("&nbsp;"," ");
                                content = content.replaceAll("&amp;"," ");
                                tvContentAboutUs.setText(content) ;
                            }

                        }
                    }else {
                        if(response.body().error_code== AppGlobal.ERROR_CODE){
                            getToken(_this);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void refreshTokenCallBack() {
        token = sharedpref.getString("access_token");
        getAboutUsDetails();
    }
}
