package com.carsgates.cr.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carsgates.cr.R;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.adapter.Page_Adapter;
import com.carsgates.cr.fragments.CarDetailTab1Fragment;
import com.carsgates.cr.fragments.CarDetailTab2Fragment;
import com.carsgates.cr.fragments.CarDetailTab3Fragment;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.CarOtherApplication;
import com.carsgates.cr.webservices.CarSpecification;
import com.carsgates.cr.webservices.Protection;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.carsgates.cr.webservices.SingleCarDetails;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDetail extends AppCompatActivity {
    TabLayout tabLayout;
    Page_Adapter adapter;
    ActionBar actionBar;
    TinyDB sharpref;
    public static String token,logo,carPrice,carImage,modelname;
    Gson gson = new Gson();
    public static SingleCarDetails singleCarDetails = new SingleCarDetails();
    public static List<CarSpecification> spec=new ArrayList<>();
    List<CarOtherApplication> otherspec=new ArrayList<>();
    public static List<Protection> protects=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        sharpref=new TinyDB(this);
        token=sharpref.getString("access_token");
        setupApi();
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setTitle("Car Detail");
        }
    }


    private void setupApi() {
        String id="4";
        RetroFitApis fitApis= RetrofitApiBuilder.getRetrofitGlobal();
        Call<ApiResponse> responseCall=fitApis.car_detail(token,id);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().status = true) {
                    logo=response.body().response.car_detail.company_logo;
                    carPrice=response.body().response.singleCarDetails.getResponse().getCar_detail().getCarmanagement_price();
                    carImage= (String) response.body().response.singleCarDetails.getResponse().getCar_detail().getCarmanagement_carimage();
                    modelname=response.body().response.singleCarDetails.getResponse().getCar_detail().getModel_name();
                    List<CarSpecification> specifiation =response.body().response.car_detail.carmanagement_specifications;
                    if(specifiation!=null)
                    {
                        for(int i=0;i<specifiation.size();i++)
                        {
                            CarSpecification specification=new CarSpecification();
                            CarSpecification carSpecification=specifiation.get(i);
                            specification.setSpecification_description(carSpecification.specification_description);
                            specification.setSpecification_name(carSpecification.specification_name);
                            specification.setSpecification_display(carSpecification.specification_display);
                            specification.setSpecification_id(carSpecification.specification_id);
                            spec.add(specification);
                        }

                    }
                    List<CarOtherApplication> otherApplications = response.body().response.car_detail.carmanagement_othspecifications;
                    if(otherApplications!=null)
                    {
                        for(int i=0;i<otherApplications.size();i++)
                        {
                            CarOtherApplication carOtherApplication=new CarOtherApplication();
                            CarOtherApplication application=otherApplications.get(i);
                            carOtherApplication.setOspecification_description(application.ospecification_description);
                            carOtherApplication.setOspecification_id(application.ospecification_id);
                            carOtherApplication.setOspecification_name(application.ospecification_name);
                            otherspec.add(carOtherApplication);
                        }
                    }
                    List<Protection> protec=response.body().response.protection;
                    if(protec!=null)
                    {
                        for(int i=0;i<protec.size();i++)
                        {
                            Protection protection=new Protection();
                            Protection pro=protec.get(i);
                            protection.setProtection_description(pro.protection_description);
                            protection.setProtection_id(pro.protection_id);
                            protection.setProtection_name(pro.protection_name);
                            protection.setProtection_language_id(pro.protection_language_id);
                            protection.setProtection_language_rid(pro.protection_language_rid);
                            protection.setProtection_status(pro.protection_status);
                            protects.add(protection);
                        }
                    }
                    if(!carImage.equals(null) || !carImage.isEmpty())
                    {
                        if (!logo.equals(null) || !logo.isEmpty()) {
                            logo = RetrofitApiBuilder.IMG_BASE_URL + logo;
                            carImage = RetrofitApiBuilder.IMG_BASE_URL + carImage;
                        }
                    }
                    handletablayout();
                }
                else
                {
                    Utility.message(CarDetail.this, response.body().msg);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Utility.message(getApplicationContext(), "Connection Error");
            }
        });
    }

    private void handletablayout() {
        View view1=getLayoutInflater().inflate(R.layout.tabstyle,null);
        View view2=getLayoutInflater().inflate(R.layout.tabstyle,null);
        View view3=getLayoutInflater().inflate(R.layout.tabstyle,null);
        view1.findViewById(R.id.img_tab).setBackgroundResource(R.drawable.ic_tab1);
        ImageView tab2= (ImageView) view2.findViewById(R.id.img_tab);
        Glide.with(getApplicationContext()).load(logo).into(tab2);
        view3.findViewById(R.id.img_tab).setBackgroundResource(R.drawable.ic_tab3);
        tabLayout= (TabLayout) findViewById(R.id.cardet_Tab);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        CarDetailTab1Fragment tab1Fragment=new CarDetailTab1Fragment();
        CarDetailTab2Fragment tab2Fragment=new CarDetailTab2Fragment();
        CarDetailTab3Fragment tab3Fragment=new CarDetailTab3Fragment();
        final ViewPager pager= (ViewPager) findViewById(R.id.cardet_viewpager);
        adapter=new Page_Adapter(getSupportFragmentManager(),tabLayout.getTabCount(),tab1Fragment,tab2Fragment,tab3Fragment);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.bottomToolBar);
        TextView textView= (TextView) toolbar.findViewById(R.id.txt_bot);
        textView.setText("Book this Car");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(CarDetail.this, ExcessProtectionActivity.class);
                it.putExtra("get","FromActi");
                startActivity(it);
            }
        });
    }

}
