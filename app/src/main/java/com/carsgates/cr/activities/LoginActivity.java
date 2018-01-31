package com.carsgates.cr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.AppGlobal;
import com.carsgates.cr.Utils.LocaleHelper;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.GetToken;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.carsgates.cr.webservices.UserDetails;
import com.mukesh.tinydb.TinyDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppBaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    AppGlobal appGlobal=AppGlobal.getInstancess();
    TextView txtWelcome, txtEmail, txtPass, txtLoginForgot;
    EditText username,password;
    LinearLayout ll_forgetpassword;
    Button bt_signup, btnSkip;
    TinyDB sharedpref;
    String token,language_code;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpref=new TinyDB(getApplicationContext());
        language_code = sharedpref.getString("language_code") ;
        token=sharedpref.getString("access_token");
        appGlobal.context=getApplicationContext();

//        find id
        ll_forgetpassword= (LinearLayout) findViewById(R.id.ll_forgetpassword);
        bt_signup= (Button) findViewById(R.id.bt_signup);
        txtWelcome = (TextView) findViewById(R.id.login_welcome);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPass = (TextView) findViewById(R.id.txtPass);
        username= (EditText) findViewById(R.id.et_email);
        password= (EditText) findViewById(R.id.et_password);
        txtLoginForgot = (TextView) findViewById(R.id.txtloginForget);
        btnSkip = (Button) findViewById(R.id.btnSkip);

        updateViews(language_code);

        bt_signup.setOnClickListener(this);
        ll_forgetpassword.setOnClickListener(this);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            /*actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);*/
        }

        setuptoolbar();
    }

    private void checktoken() {
        String value=sharedpref.getString("access_token");
        if(value==null || value.isEmpty())
        {
            value= GetToken.checktoken(this);
            checktoken();
        }
    }

    private void setuptoolbar() {
        toolbar= (Toolbar) findViewById(R.id.bottomToolBar);
        TextView textView= (TextView)toolbar.findViewById(R.id.txt_bot);
        textView.setText(getResources().getString(R.string.sign_in));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    String UserId,Msg;
    private void login() {
        InputMethodManager methodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(toolbar.getWindowToken(),0);
        String user=username.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if (!user.isEmpty() && !pass.isEmpty())
        {
            if(!Utility.isNetworkConnected(getApplicationContext())){
                Toast.makeText(LoginActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
                return;
            }
            Utility.showloadingPopup(this);
            RetroFitApis retroFitApis= RetrofitApiBuilder.getRetrofitGlobal();
            Call<ApiResponse> responseCall=retroFitApis.login(token,user,pass);
            responseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    Utility.hidepopup();
                    if(response.body().status==true)
                    {
                        appGlobal.setUser_id(response.body().response.userdetail.user_id);
                        appGlobal.setUser_attachment(response.body().response.userdetail.user_attachment);
                        appGlobal.setUser_address(response.body().response.userdetail.user_address);
                        appGlobal.setUser_branch_id(response.body().response.userdetail.user_branch_id);
                        appGlobal.setUser_company_id(response.body().response.userdetail.user_company_id);
                        appGlobal.setUser_countrycode(response.body().response.userdetail.user_countrycode);
                        appGlobal.setUser_department_id(response.body().response.userdetail.user_department_id);
                        appGlobal.setUser_email(response.body().response.userdetail.user_email);
                        appGlobal.setUser_empcode(response.body().response.userdetail.user_empcode);
                        appGlobal.setUser_image(response.body().response.userdetail.user_image);
                        appGlobal.setUser_language_id(response.body().response.userdetail.user_language_id);
                        if(response.body().response.userdetail.user_lname!=null) {
                            appGlobal.setUser_lname(response.body().response.userdetail.user_lname);
                        }else{
                            appGlobal.setUser_lname("");
                        }
                        appGlobal.setUser_name(response.body().response.userdetail.user_name);
                        appGlobal.setUser_password(response.body().response.userdetail.user_password);
                        appGlobal.setUser_phone(response.body().response.userdetail.user_phone);
                        appGlobal.setUser_status(response.body().response.userdetail.user_status);
                        appGlobal.setUser_type(response.body().response.userdetail.user_type);
                        Log.d("TAG", "onResponse: "+response.body().response.userdetail.user_type);
                        appGlobal.setUser_username(response.body().response.userdetail.user_username);
                        appGlobal.setUser_zipcode(response.body().response.userdetail.user_zipcode);

                        String st=  appGlobal.getUser_id();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Utility.message(getApplicationContext(), response.body().msg);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Utility.hidepopup();
                    Utility.message(getApplicationContext(),"Connection Error");
                }
            });
        }
        else {
            if (user.isEmpty() && pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please Enter username and Password.", Toast.LENGTH_SHORT).show();
            } else {
                if (user.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                }
                if (pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void skipLogin(View view) {
        sharedpref.putBoolean("isSkipLogin",true);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();

        txtPass.setText(R.string.enter_password);
        txtEmail.setText(R.string.enter_email);
        username.setHint(R.string.enter_email);
        password.setHint(R.string.enter_password);
        txtPass.setHint(R.string.enter_password);
        txtWelcome.setText(R.string.Welcome);
        bt_signup.setText(R.string.sign_up);
        txtLoginForgot.setText(R.string.login_forgetpassord);
        password.setTypeface(username.getTypeface());
        password.setOnEditorActionListener(this);
        btnSkip.setText(R.string.skip);

    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(R.string.login);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.ll_forgetpassword:
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;
            case R.id.bt_signup:
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.et_password:
                login();
                break;
        }
        return false;
    }
}
