package com.carsgates.cr.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.RuntimePermissions;
import com.carsgates.cr.webservices.AboutUs;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.ContactUs;
import com.carsgates.cr.webservices.Data;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Atul Kumar Gupta on
 * Contact Number : +91 8470967433
 */

public class ContactUsActivity extends RuntimePermissions implements View.OnClickListener {
    View v;
    TelephonyManager tm;
    TextView mPhonenumber,tvContactEmail;
    ActionBar actionBar;
    boolean check=false;
    int PERMISSION_CODE=20;
    TinyDB sharedpref;
    String token,language_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        v=findViewById(android.R.id.content);
        sharedpref=new TinyDB(getApplicationContext());
        token=sharedpref.getString("access_token");
        language_code = sharedpref.getString("language_code");
        tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhonenumber= (TextView) findViewById(R.id.call_Phonenumber);
        tvContactEmail= (TextView) findViewById(R.id.tvContactEmail);
        mPhonenumber.setOnClickListener(this);
        tvContactEmail.setOnClickListener(this);
        checksimstate();
        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }

    @Override
    protected void onPermissiongranted(int requestCode) {
        check=true;
    }

    private void checksimstate() {
        int simState=tm.getSimState();
        switch (simState)
        {
            case TelephonyManager.SIM_STATE_ABSENT:
                Snackbar.make(v,"Please Insert Sim in your Phone",Snackbar.LENGTH_LONG).show();
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                Snackbar.make(v,"Please Wait Untill we get Sim State",Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    private void callnumber(String No) {
        Intent call=new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:"+No));
        startActivity(Intent.createChooser(call,"Select an App"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(getResources().getString(R.string.action_contact_us));
        getContactUsDetails();
    }

    private void getContactUsDetails() {
        RetroFitApis retroFitApis= RetrofitApiBuilder.getRetrofitGlobal();
        Call<ApiResponse> responseCall=retroFitApis.contact_us(token,language_code);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body()!=null) {
                    if (response.body().status) {
                        Data data = response.body().response;
                        if (data != null) {
                            ContactUs contact_us  =  data.contact_us ;
                            String email = contact_us.sitesetting_email ;
                            String phone = contact_us.sitesetting_phone ;
                            tvContactEmail.setText(email);
                            mPhonenumber.setText(phone);
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
    public void onClick(View v) {
        ContactUsActivity.super.requestAppPermissions(new String[]{Manifest.permission.CALL_PHONE},
                R.string.permissionsetting,PERMISSION_CODE);
        if(check) {

            switch (v.getId()) {
                case R.id.call_Phonenumber:
                    String numbers = mPhonenumber.getText().toString();
                    callnumber(numbers);
                    break;
                case R.id.tvContactEmail:
                    String email = tvContactEmail.getText().toString();
                    openEmail(email) ;
                    break;
            }
        }
    }

    private void openEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email}) ;
        try {
            startActivity(Intent.createChooser(intent, "Send mail"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "here are no email applications installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
