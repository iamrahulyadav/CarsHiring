package com.carsgates.cr.activities;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.LocaleHelper;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.adapter.LocationAdapter;
import com.carsgates.cr.interfaces.IRefreshToken;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.Data;
import com.carsgates.cr.webservices.Location;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Atul Kumar Gupta on 5/3/2017.
 * Contact Number : +91 8470967433
 */

public class LocationSelectionActivity extends AppBaseActivity {
    public static final String RESPONSE_DATA = "location" ;
    public static final int RESPONSE_LOCATION = 200;
    LocationAdapter adapter ;
    RecyclerView rvLocations ;
    EditText etSearchLocation;
    private String token,cityname, languagecode;


    List<Location> listLocations;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        tinyDB = new TinyDB(getApplicationContext());
        token  = tinyDB.getString("access_token") ;
        languagecode = tinyDB.getString("language_id");
        setLanguages(languagecode);
        actionBar=getSupportActionBar();
        if(actionBar!=null) actionBar.hide();

        etSearchLocation  =  (EditText) findViewById(R.id.etSearchLocation) ;
        etSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query  = charSequence.toString().trim() ;
                refreshList(query);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        rvLocations = (RecyclerView) findViewById(R.id.rvLocations) ;
        listLocations =  new ArrayList<>();
        RecyclerView.LayoutManager layoutManager  =  new LinearLayoutManager(this);
        rvLocations.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rvLocations.addItemDecoration(itemDecoration);
        adapter = new LocationAdapter(listLocations, new LocationAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Location location) {
                Intent intent  =  new Intent();
                intent.putExtra(RESPONSE_DATA,location);
                setResult(RESPONSE_LOCATION,intent);
                finish();
            }
        });
        rvLocations.setAdapter(adapter);
        getLocationList();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
    public void setLanguages(String language_code){
        Locale locale = new Locale(language_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void getLocationList() {

        final LocationSelectionActivity _this =  this ;
        Utility.showLoading(_this,"Finding Location List...");
        RetroFitApis retroFitApis =  RetrofitApiBuilder.getRetrofitGlobal() ;
        Call<ApiResponse> responseCall = retroFitApis.location_list(token) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Utility.hidepopup();
                if(response.body()!=null){
                    if(response.body().status){
                        Data data  = response.body().response ;
                        if(data!=null){
                            listLocations.addAll(data.locations_list);
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        if(response.body().error_code==102)
                            getToken(_this);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Utility.hidepopup();
                Toast.makeText(LocationSelectionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void refreshList(String query) {
        final List<Location> filterList = new ArrayList<>();
        for (Location location:  listLocations) {
            if(location.city_name.trim().toLowerCase().contains(query.toLowerCase())){
                filterList.add(location);
            }
        }

        adapter = new LocationAdapter(filterList, new LocationAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Location location) {
                Intent intent  =  new Intent();
                intent.putExtra(RESPONSE_DATA,location);
                setResult(RESPONSE_LOCATION,intent);
                finish();
            }
        });
        rvLocations.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle("Select Location");
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
        token = tinyDB.getString("access_token");
        getLocationList();
    }
}
