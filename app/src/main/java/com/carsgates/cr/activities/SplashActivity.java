package com.carsgates.cr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.AppGlobal;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.LanguageModel;
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

public class SplashActivity extends AppBaseActivity {
    public String accessToken,languageselected;;
    TinyDB sharedpref;
    Spinner spinner_language;
    View v;
    ArrayList<String> langlistname,langlistcode,langlistId  ;
    String[] lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpref=new TinyDB(getApplicationContext());
        String language_code = sharedpref.getString("language_code") ;
        boolean isSkipLogin = sharedpref.getBoolean("isSkipLogin");
        if(language_code!=null && !language_code.isEmpty()) {
//            updateResources(this, language_code);
            if (isSkipLogin) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else if (sharedpref.contains("userid")){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
        } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));finish();
            }
            finish();
            return ;
        }

        v=findViewById(android.R.id.content);
        langlistname=new ArrayList<>();
        langlistcode=new ArrayList<>();
        langlistId=new ArrayList<>();
        lan=new String[langlistname.size()];
        spinner_language = (Spinner) findViewById(R.id.spinner_language);


        if(actionBar!=null) actionBar.hide();
        accessToken = sharedpref.getString("access_token");
        if(accessToken!=null && !accessToken.isEmpty()) {
            getLanguageList();
        }else{
            getAccessToken();
        }

    }

    private static boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return true;
    }



    private void getLanguageList() {
        final  SplashActivity _this =  this ;
        RetroFitApis apis=RetrofitApiBuilder.getRetrofitGlobal();
        final Call<ApiResponse> responseCall=apis.lang_list(sharedpref.getString("access_token"));
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body()!=null) {

                    if(response.body().status) {
                        List<LanguageModel> language_list = response.body().response.language_list;
                        if (language_list != null) {
                            langlistname.add("Select Language");
                            langlistcode.add("");

                            langlistId.add("");
                            for (int i = 0; i < language_list.size(); i++) {
                                LanguageModel languages = language_list.get(i);
                                String languageId = languages.language_id;
                                String languageCode = languages.language_code;
                                String languageName = languages.language_name;
                                langlistname.add(languageName);
                                langlistcode.add(languageCode);
                                langlistId.add(languageId);
                            }
                            lan = langlistname.toArray(lan);
                            handlespinner();
                        } else {
                            Utility.message(SplashActivity.this, response.body().msg);
                        }
                    }else{
                        if(response.body().error_code==102){
                        }
                        Toast.makeText(_this, response.body().msg, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SplashActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAccessToken() {
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal();
        String grant_type = "client_credentials";
        String client_id = "developer";
        String client_secret = "5a633cf4392e8";
        Call<ApiResponse> apiResponseCall = retroFitApis.token(grant_type, client_id, client_secret);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body()!=null){
                    accessToken = response.body().access_token;
                    if(accessToken!=null && !accessToken.isEmpty()) {
                        sharedpref.putString("access_token", accessToken);
                        getLanguageList();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                spinner_language.setClickable(false);
                spinner_language.setEnabled(false);
            }
        });
    }

    private void handlespinner() {
        final ArrayAdapter<String> adapt=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,lan);
        adapt.setDropDownViewResource(R.layout.spinner_item);
        spinner_language.setAdapter(adapt);
        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0) {
                    languageselected = (String) adapterView.getItemAtPosition(i);
                    String language_code  = langlistcode.get(i);
                    String language_id=langlistId.get(i);
                    sharedpref.putString("language_code",language_code);
                    sharedpref.putString("language_id",language_id);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Utility.message(getApplicationContext(),"no");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checknetwork();
    }
    public void checknetwork() {
        if(!Utility.isNetworkConnected(this))
        {
            spinner_language.setClickable(false);
            spinner_language.setEnabled(false);
            Snackbar.make(v,"Check Your Internet Connection",Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checknetwork();
                }
            }).setActionTextColor(getResources().getColor(R.color.redStrong)).show();
        }
        else
        {

        }
    }

    /*@Override
    public void refreshTokenCallBack() {
        getLanguageList();
    }*/
}
