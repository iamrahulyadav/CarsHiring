package com.carsgates.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.adapter.DriverAdapter;
import com.carsgates.cr.models.DriverModel;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.Driver;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sony on 04-05-2017.
 */

public class DriverSelectionActivity extends AppBaseActivity implements DriverAdapter.ClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    RecyclerView driver_list;
    DriverAdapter driverAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String FromQuotes="";
    List<DriverModel> driverlists=new ArrayList<>();
    TinyDB sharedpref;
    String token,userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_drivers_selection);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        sharedpref=new TinyDB(getApplicationContext());
        userid=sharedpref.getString("userid");
        token=sharedpref.getString("access_token");

        Intent intent=getIntent();
        if(intent!=null) {
            FromQuotes = intent.getStringExtra("FromQuotes");
            /*if(FromQuotes!=null) {
                if (FromQuotes.equalsIgnoreCase("Yes")) {
                    Toast.makeText(getApplicationContext(), "Well", Toast.LENGTH_SHORT).show();
                }
            }*/
        }
        driver_list= (RecyclerView) findViewById(R.id.rec_driver_list);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipedriver);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.redStrong));
        swipeRefreshLayout.setOnRefreshListener(this);

        driverAdapter=new DriverAdapter(getApplicationContext(),driverlists);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getBaseContext());
        driver_list.setLayoutManager(layoutManager);
        driverAdapter.setClickListene(this);
        driver_list.setAdapter(driverAdapter);
        driver_list.addItemDecoration(new DividerItemDecoration(getBaseContext(),DividerItemDecoration.VERTICAL));
        setuptoolbar();

    }

    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.bottomToolBar);
        TextView txt = (TextView) toolbar.findViewById(R.id.txt_bot);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.img_bot);
        imageView.setImageResource(R.drawable.ic_add);
        txt.setText("Add");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverSelectionActivity.this,AddDriverActivity.class));
            }
        });
    }

    private void preapredata() {
        if(driverlists!=null)
        {
            driverlists.clear();
        }
        else
        {
            Utility.message(getApplicationContext(),"No record Found");
        }
        Utility.showloadingPopup(DriverSelectionActivity.this);
        RetroFitApis fitApis= RetrofitApiBuilder.getRetrofitGlobal();
        Call<ApiResponse> responseCall=fitApis.driver_list(token,userid);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Utility.hidepopup();
                if(response.body().status==true)
                {
                    List<Driver> driverList=response.body().response.driver_list;
                    if(!driverList.isEmpty()) {
                        for (int i = 0; i < driverList.size(); i++) {
                            DriverModel driverModel=new DriverModel();
                            Driver  driver=driverList.get(i);
                            driverModel.setDriver_fname(driver.driver_fname);
                            driverModel.setDriver_sname(driver.driver_lname);
                            driverModel.setDriver_email(driver.driver_email);
                            driverModel.setDriver_id(driver.driver_id);
                            driverModel.setDriverImg(R.drawable.ic_driver1);
                            driverModel.setDriver_phone(driver.driver_phone);
                            driverModel.setDriver_age(driver.driver_age);
                            driverModel.setDriver_title(driver.driver_title);
                            driverModel.setDriver_branch_id(driver.driver_branch_id);
                            driverModel.setDriver_carmanagement_id(driver.driver_carmanagement_id);
                            driverModel.setDriver_countrycode(driver.driver_countrycode);
                            driverModel.setDriver_address(driver.driver_address);
                            driverModel.setDriver_language_id(driver.driver_language_id);
                            driverModel.setDriver_nationalid(driver.driver_nationalid);
                            driverlists.add(driverModel);
                        }
                        driverAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Utility.message(getApplicationContext(), response.body().msg);
                    }
                }
                else
                {
                         Utility.message(getApplicationContext(), response.body().msg);
                    //access token is empty.
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Utility.message(getApplicationContext(),"Connection Error");
                Utility.hidepopup();
            }
        });
       /* for (int i=0;i<5;i++) {
            DriverModel model = new DriverModel("Ajay", "Ajay@mail.com", "1234567891", "26", R.drawable.ic_driver1);
            driverlist.add(model);
            model = new DriverModel("Abc", "Abc@mail.com", "15987632486", "29", R.drawable.ic_driver2);
            driverlist.add(model);
            model = new DriverModel("John", "John@mail.com", "9876543210", "25", R.drawable.ic_driver3);
            driverlist.add(model);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        preapredata();
        actionBar.setTitle("Select driver");
    }

    @Override
    public void itemClicked(View view, int postion) {
        if(FromQuotes!=null) {
            startActivity(new Intent(DriverSelectionActivity.this, ThankYou.class));
        }
        else
        {
            startActivity(new Intent(DriverSelectionActivity.this, PaymentDetails.class));
        }
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getBaseContext(),"Loading",Toast.LENGTH_SHORT).show();
        driverAdapter=new DriverAdapter(getApplicationContext(),driverlists);
        driver_list.setAdapter(driverAdapter);
        driverAdapter.setClickListene(this);
        swipeRefreshLayout.setRefreshing(false);
        preapredata();
    }
}
