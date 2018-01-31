package com.carsgates.cr.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.carsgates.cr.R;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.adapter.SpinnerAdapter;
import com.carsgates.cr.models.SpinnerItemModel;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;
import com.mukesh.tinydb.TinyDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends AppCompatActivity {
    private TinyDB sharedpref;
    ActionBar actionBar;
    EditText et_cardname,et_number, et_cvv,et_zipcode;
    String Country,Expdate,userId,token,CardId;
    TextView countryselect,txt_monthYear;
    DatePickerDialog expriydate;
    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        et_cardname= (EditText) findViewById(R.id.et_cardholdername);
        et_number= (EditText) findViewById(R.id.et_cardnumber);
        et_zipcode= (EditText) findViewById(R.id.et_zipcode);
        et_cvv=(EditText) findViewById(R.id.et_cvv);
        countryselect= (TextView) findViewById(R.id.txt_countryselect);
        txt_monthYear= (TextView) findViewById(R.id.et_mon_year);
        sharedpref = new TinyDB(getApplicationContext());
        userId=sharedpref.getString("userid");
        token=sharedpref.getString("access_token");

        countryselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectyourcountry();
            }
        });
        txt_monthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterdate();
            }
        });
        txt_monthYear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                clickonDrawable(v, event);
                return false;
            }
        });
        et_cvv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clickonDrawable(v, event);
                return false;
            }
        });
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("Add Card");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        it=getIntent();
        CardId=it.getStringExtra("cardId");
        if(CardId!=null && !CardId.isEmpty()) {
            actionBar.setTitle("View Card");
            updatecarddetails();
        }
        setuptoolbar();
    }

    private void updatecarddetails() {
        String name = it.getStringExtra("name");
        String number = it.getStringExtra("number");
        String expiry = it.getStringExtra("expiry");
        String cvv = it.getStringExtra("cvv");
        String country = it.getStringExtra("country");
        String zipcode = it.getStringExtra("zipcode");
        if (!name.isEmpty() && !name.equals(null)) {
            et_cardname.setText(name);
            et_cardname.setEnabled(false);
        }
        if (!number.isEmpty() && number!=null) {
            String CreditCardFormat="XXXX XXXX XXXX "+number.substring(number.length()-4);
            int length=20;
            InputFilter[] filters=new InputFilter[1];
            filters[0]=new InputFilter.LengthFilter(length);
            et_number.setFilters(filters);
            et_number.setText(CreditCardFormat);
            et_number.setEnabled(false);
        }
        if (!expiry.isEmpty() && expiry!=null) {
            txt_monthYear.setText(expiry);
            txt_monthYear.setEnabled(false);
        }
        if (!cvv.isEmpty() && cvv!=null) {
            cvv="XXX";
            et_cvv.setText(cvv);
            et_cvv.setEnabled(false);
        }
        if (!country.isEmpty() && country!=null) {
            countryselect.setText(country);
            countryselect.setEnabled(false);
        }
        if (!zipcode.isEmpty() && zipcode!=null) {
            et_zipcode.setText(zipcode);
            et_zipcode.setEnabled(false);
        }
        et_zipcode.setEnabled(false);
    }

    private void enterdate() {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        expriydate=new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal= Calendar.getInstance();
                cal.set(Calendar.YEAR,year);
                cal.set(Calendar.MONTH,month);
                cal.set(Calendar.DATE,dayOfMonth);
                String formaat="dd-MM-yyyy";
                SimpleDateFormat  dateFormat=new SimpleDateFormat(formaat);
                Expdate=dateFormat.format(cal.getTime());
                txt_monthYear.setText(Expdate);
            }
        },year,month,day);
        expriydate.getDatePicker().setMinDate(calendar.getTime().getTime());
        expriydate.setTitle("Select Expiry Date");
        expriydate.show();

    }

    private void selectyourcountry() {
        final CountryPicker picker=CountryPicker.newInstance("Select Country");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                Country=name;
                //    Utility.message(getApplicationContext(),name+" "+code+" "+dialCode+" "+flagDrawableResID);
                countryselect.setText(Country);
                countryselect.setCompoundDrawablesWithIntrinsicBounds(flagDrawableResID,0,R.drawable.simple_spinner_arrow,0);
                countryselect.setCompoundDrawablePadding(7);
                countryselect.setGravity(Gravity.CENTER_VERTICAL);
                picker.dismiss();
            }
        });
        picker.show(getSupportFragmentManager(),"COUNTRY_SELECTOR");
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.bottomToolBar);
        final TextView textView= (TextView)toolbar.findViewById(R.id.txt_bot);
        final ImageView imageView= (ImageView)toolbar.findViewById(R.id.img_bot);
        textView.setText("Save");
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        if(CardId!=null && !CardId.isEmpty()) {
            textView.setText("Done");
            imageView.setVisibility(View.GONE);
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CardId!=null && !CardId.isEmpty()) {
                    finish();
                }
                else {
                    setupApi();
                }
              /*  onBackPressed();
                overridePendingTransition(R.anim.up_from_bottom,R.anim.bottom_from_up);*/
                //  finish();
            }
        });
    }

    private void setupApi() {
        String name= et_cardname.getText().toString().trim();
        String number=et_number.getText().toString().trim();
        String cvv=et_cvv.getText().toString().trim();
        String zipcode=et_zipcode.getText().toString().trim();
        RetroFitApis retroFitApis= RetrofitApiBuilder.getRetrofitGlobal();
        if(name!=null && number!=null && cvv!=null&& Expdate!=null && Country!=null) {
            if (!name.isEmpty() && !number.isEmpty() && !cvv.isEmpty() && !Expdate.isEmpty() && !Country.isEmpty()) {
                if (number.length() < 16) {
                    Utility.message(getApplicationContext(), "Enter Valid Card Number");
                } else {
                    Call<ApiResponse> call = retroFitApis.add_card(token, name, number, Expdate, cvv, Country, zipcode, userId);
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.body() != null) {
                                if (response.body().status == true) {
                                    Utility.message(getApplicationContext(), response.body().msg);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Utility.message(getApplicationContext(), "Connection error");
                        }
                    });
                }
            } else {
                Utility.message(getApplication(), "Fill all the Fields.");
            }
        }
        else {
            Utility.message(getApplication(), "Fill Enter all the Fields.");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.up_from_bottom,R.anim.bottom_from_up);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    boolean clickonDrawable(View v,MotionEvent event)
    {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.getRawX() >= (txt_monthYear.getRight() - txt_monthYear.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Toast.makeText(getApplication(),"Help",Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
