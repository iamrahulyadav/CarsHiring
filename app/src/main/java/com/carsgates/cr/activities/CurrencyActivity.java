package com.carsgates.cr.activities;

import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.databinding.ActivityCurrencyBinding;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.CarDetails;
import com.carsgates.cr.webservices.Driver;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyActivity extends AppCompatActivity {
    RadioGroup curgroup;
   // ApiResponse apiResponse=ApiResponse.getInatance();
    TinyDB sharedpre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        sharedpre=new TinyDB(getApplicationContext());
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("Currency Selection");
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        curgroup=(RadioGroup) findViewById(R.id.CurencyGroup);
        ((RadioButton)curgroup.getChildAt(0)).setChecked(true);
        curgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton= (RadioButton) curgroup.findViewById(curgroup.getCheckedRadioButtonId());
                if (radioButton!=null)
                {
                    radioButton.setChecked(true);
                }
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



    /***************************************************
     *
     * API References
     *
     ****************************************************/


    public String accessToken ;
    public void getToken(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String grant_type = "client_credentials";
        String id = "testclient" ;
        String secret = "testpass" ;
        Call<ApiResponse> responseCall = retroFitApis.token(grant_type,id,secret);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
           //  apiResponse.setAccess_token(response.body().access_token);
               accessToken =  response.body().access_token ;
                Toast.makeText(CurrencyActivity.this, "Connection successfull"+response.body().access_token , Toast.LENGTH_SHORT).show();
                sharedpre.putString("access_token",accessToken);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String userId ;
    public String password ;
    public void doLogin(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String username =  "muhib@askonlinesolutions.com";
        password = "12345";
       //String access=apiResponse.getAccess_token();
        Call<ApiResponse> responseCall = retroFitApis.login(accessToken,username,password) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Utility.hidepopup();
                userId  =  response.body().response.userdetail.user_id ;
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
                Log.d("Muhib",response.message());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                Utility.hidepopup();
            }
        });
    }

    public void doSignUp(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String email =  "muhib@askonlinesolutions.com";
        String pass = "12345" ;
        Call<ApiResponse> responseCall = retroFitApis.signup(accessToken,email,pass) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void forgotPassword(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String email =  "atul@askonlinesolutions.com";

        Call<ApiResponse> responseCall = retroFitApis.forgot_pass(accessToken,email) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePassword(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String newPassword = password;
        password = "ask@12345678";
        Call<ApiResponse> responseCall = retroFitApis.change_pass(accessToken,password,newPassword,userId) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLanguages(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;

        Call<ApiResponse> responseCall = retroFitApis.lang_list(accessToken) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            for(int i=0;i<response.body().response.language_list.size();i++)
            {

            }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getContactUs(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;

        Call<ApiResponse> responseCall = retroFitApis.contact_us(accessToken,"en") ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAboutUs(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;

        Call<ApiResponse> responseCall = retroFitApis.about_us(accessToken,"en") ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    List<CarDetails> cars ;
    public void getCarDetails(View view) {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String car_id = cars.get(new Random().nextInt(cars.size())).carmanagement_id;
        Call<ApiResponse> responseCall = retroFitApis.car_detail(accessToken,car_id) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getDriverList() {

        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;

        Call<ApiResponse> responseCall = retroFitApis.driver_list(accessToken,userId) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* public void addDriver() {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String title ="Mr";
        int age=21;
        String fname="Manoj";
        String lname="pal";
        String email="manoj@gmail.com";
        String phone="0987654321";
        Call<ApiResponse> responseCall = retroFitApis.add_driver(accessToken,title,age,fname,lname,email,phone,userId) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    List<Driver> drivers ;
    public void getDriverDetails() {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;
        String driver_id= drivers.get(new Random().nextInt(drivers.size())).driver_id;
        Call<ApiResponse> responseCall = retroFitApis.driver_detail(accessToken,userId,driver_id) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLocationList() {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;

        Call<ApiResponse> responseCall = retroFitApis.location_list(accessToken) ;
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(CurrencyActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CurrencyActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
