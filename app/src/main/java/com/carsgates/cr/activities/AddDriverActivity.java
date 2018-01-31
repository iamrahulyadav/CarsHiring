package com.carsgates.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.AppGlobal;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sony on 05-05-2017.
 */

public class AddDriverActivity extends AppBaseActivity {
    AppGlobal appGlobal=AppGlobal.getInstancess();
    EditText etfirstname,etAge,etlastname,etemail,etpassportno,etphonenumber,etdrivinglicenseno;
    Spinner spdriverTitle,sporignoflicesense;
    String firstname,lastname,ages,email,passportno,phonenumber,drivingLicNo,title,licencountry,
            savedriverdetail,token,userid,savenewsletter,driversid,titles;
    SwitchCompat saveDriver;
    TinyDB sharedpref;
    CheckBox newsletter;
    ArrayList<String> countries=new ArrayList<>();


    int age;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        sharedpref=new TinyDB(getApplicationContext());
        appGlobal.context=getApplicationContext();
        userid=sharedpref.getString("userid");
        token=sharedpref.getString("access_token");
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        saveDriver= (SwitchCompat) findViewById(R.id.sw_Savedriver);
        newsletter= (CheckBox) findViewById(R.id.ch_subs);
        Intent it=getIntent();
        driversid=it.getStringExtra("driverID");
        if(driversid!=null && !driversid.isEmpty())
        {
            setdriverdetails();
            titles="Edit Driver";
        }
        else
        {
            titles="Add Driver";
        }
        spdriverTitle= (Spinner) findViewById(R.id.sp_drivertitle);
        sporignoflicesense=(Spinner) findViewById(R.id.sp_licesenseorigin);
        setupedittext();
        setupSpinners();
        setuptoolbar();
    }

    private void setdriverdetails() {
        RetroFitApis retroFitApis=RetrofitApiBuilder.getRetrofitGlobal();
        Call<ApiResponse> responseCall=retroFitApis.driver_detail(token,userid,driversid);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body()!=null) {
                    etAge.setText(response.body().response.driver_detail.driver_age);
                    etfirstname.setText(response.body().response.driver_detail.driver_fname);
                    if (response.body().response.driver_detail.driver_lname !=null){
                        etlastname.setText(response.body().response.driver_detail.driver_lname);
                    }
                    etemail.setText(response.body().response.driver_detail.driver_email);
                    etphonenumber.setText(response.body().response.driver_detail.driver_phone);
                    etdrivinglicenseno.setText(response.body().response.driver_detail.driver_driving_license);
                    etpassportno.setText(response.body().response.driver_detail.driver_nationalid);
                    if (response.body().response.driver_detail.driver_title!=null
                            && response.body().response.driver_detail.driver_license_origin!=null){
                        String dtitle = response.body().response.driver_detail.driver_title;
                        String dlicCountry = response.body().response.driver_detail.driver_license_origin;
                        updateSpinners(dtitle,dlicCountry);
                    }
                    savedriverdetail= response.body().response.driver_detail.driver_save_next;
                    if (savedriverdetail.equals("1")){
                        saveDriver.setChecked(true);
                    }

                    savenewsletter = response.body().response.driver_detail.driver_save_next;
                    if (savenewsletter.equals("1")){
                        newsletter.setChecked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    private void updateSpinners(String t, String dlicCountry) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Titles, android.R.layout.simple_spinner_item);
        spdriverTitle.setAdapter(adapter);
        if(!t.equals(null))
        {
            int i=adapter.getPosition(t);
            spdriverTitle.setSelection(i);
        }
        Locale[] locales=Locale.getAvailableLocales();

        String Country;
        for (Locale loc:locales) {
            Country = loc.getDisplayCountry();
            if (Country.length() > 0 && !countries.contains(Country)) {
                countries.add(Country);
            }
        }
        Collections.sort(countries,String.CASE_INSENSITIVE_ORDER);
        countries.add(0,"Select Country");
        ArrayAdapter<String> adapt=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,countries);
        sporignoflicesense.setAdapter(adapt);
        if(!dlicCountry.equals(null))
        {
            int i=adapt.getPosition(dlicCountry);
            sporignoflicesense.setSelection(i);
        }
    }

    private void setupSpinners() {

        spdriverTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Locale[] locales=Locale.getAvailableLocales();

        String Country;
        for (Locale loc:locales) {
            Country = loc.getDisplayCountry();
            if (Country.length() > 0 && !countries.contains(Country)) {
                countries.add(Country);
            }
        }
        Collections.sort(countries,String.CASE_INSENSITIVE_ORDER);
        countries.add(0,"Select Country");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,countries);
        sporignoflicesense.setAdapter(adapter);
        sporignoflicesense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    licencountry = (String) parent.getItemAtPosition(position);
                    //   parent.getSelectedItem()
                }
                else
                {
                    licencountry="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupedittext() {
        etfirstname= (EditText) findViewById(R.id.et_fname);
        etlastname= (EditText) findViewById(R.id.et_lname);
        etemail= (EditText) findViewById(R.id.et_email);
        etphonenumber= (EditText) findViewById(R.id.et_phonenumber);
        etphonenumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        etAge=(EditText) findViewById(R.id.et_age);
        etpassportno= (EditText) findViewById(R.id.et_passportNo);
        etdrivinglicenseno= (EditText) findViewById(R.id.et_drivlicenseNo);
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.bottomToolBar);
        TextView textView= (TextView) toolbar.findViewById(R.id.txt_bot);
        textView.setText("Continue");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(driversid==null || driversid.isEmpty()) {
                    adddriver();
                }
                else
                {
                    Updatedriverdetails();
                }
            }
        });
    }

    private void Updatedriverdetails() {
        ages=etAge.getText().toString().trim();
        age= (!ages.equals("") ||!ages.isEmpty())?Integer.parseInt(ages): 0;
        firstname=etfirstname.getText().toString().trim();
        lastname=etlastname.getText().toString().trim();
        email=etemail.getText().toString().trim();
        phonenumber=etphonenumber.getText().toString().trim();
        passportno=etpassportno.getText().toString().trim();
        drivingLicNo=etdrivinglicenseno.getText().toString().trim();
        setupswitch();
        setupcheckbox();
        if(!firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !phonenumber.isEmpty()
                && !drivingLicNo.isEmpty() && !licencountry.isEmpty())
        {
            if(Utility.checkemail(email))
            {
                if(Utility.checkphone(phonenumber)) {
                    if (age >= 25 && age <= 70) {
                        Utility.showloadingPopup(this);
                        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal();
                        Call<ApiResponse> responseCall = retroFitApis.update_driver(token, title, age,
                                firstname, lastname, email, phonenumber, userid, passportno, drivingLicNo,
                                savedriverdetail, savenewsletter, licencountry, driversid);
                        responseCall.enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                Utility.hidepopup();
                                if (response.body() != null) {
                                    if (response.body().status == true) {
                                        Utility.message(getApplicationContext(), response.body().msg);
                                        finish();
                                    } else {
                                        Utility.message(getApplicationContext(), response.body().msg);
                                    }
                                } else {
                                    Utility.message(getApplicationContext(), "Connection Error");
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                Utility.hidepopup();
                                Utility.message(getApplicationContext(), "Connection Error");
                            }
                        });
                    } else {
                        Utility.message(this, "Drivers age must between 20 and 99");
                    }
                }
                else {
                    Utility.message(this, "Please enter valid Phone Number");
                }
            }
            else
            {
                Utility.message(this, "Please Enter valid Email");
            }
        }
        else
        {
            Utility.message(this, "Please Fill all fields");
        }
        //  Utility.message(getApplicationContext(),userid);
    }

    private void adddriver() {
        ages=etAge.getText().toString().trim();
        age= (!ages.equals("") ||!ages.isEmpty())?Integer.parseInt(ages): 0;
        firstname=etfirstname.getText().toString().trim();
        lastname=etlastname.getText().toString().trim();
        email=etemail.getText().toString().trim();
        phonenumber=etphonenumber.getText().toString().trim();
        passportno=etpassportno.getText().toString().trim();
        drivingLicNo=etdrivinglicenseno.getText().toString().trim();
        setupswitch();
        setupcheckbox();
        if(!firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !phonenumber.isEmpty() && !drivingLicNo.isEmpty() && !licencountry.isEmpty())
        {
            if(Utility.checkemail(email))
            {
                if(age>=25 && age<=70)
                {
                    Utility.showloadingPopup(this);
                    RetroFitApis retroFitApis= RetrofitApiBuilder.getRetrofitGlobal();
                    Call<ApiResponse> responseCall=retroFitApis.add_driver(token,title,age,firstname,
                            lastname,email,phonenumber,userid,passportno,drivingLicNo,savedriverdetail,savenewsletter,licencountry);
                    responseCall.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            Utility.hidepopup();

                            if(response.body()!=null) {
                                if(response.body().status!=false) {
                                    Utility.message(getApplicationContext(), response.body().msg);
                                    finish();
                                }
                                else
                                {
                                    Utility.message(getApplicationContext(),"Connection Error");
                                }
                            }
                            else
                            {
                                Utility.message(getApplicationContext(),"Connection Error");
                            }


                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Utility.hidepopup();
                            Utility.message(getApplicationContext(),"Connection Error");
                        }
                    });
                }
                else
                {
                    Utility.message(this,"Drivers age must between 20 and 99");
                }
            }
            else
            {

                Utility.message(this, "Please Enter valid Email");
            }
        }
        else
        {
            if(licencountry.isEmpty())
            {
                Utility.message(this, "Please Select Origin of License");
            }
            else {
                Utility.message(this, "Please Fill all fields");
            }
        }
        //  Utility.message(getApplicationContext(),userid);

    }

    private void setupcheckbox() {
        if(newsletter.isChecked())
        {
            savenewsletter="1";
        }
        else
        {
            savenewsletter="0";
        }
    }

    private void setupswitch() {
        if(saveDriver.isChecked())
        {
            savedriverdetail="1";
        }
        else
        {
            savedriverdetail="0";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(titles);

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
}
