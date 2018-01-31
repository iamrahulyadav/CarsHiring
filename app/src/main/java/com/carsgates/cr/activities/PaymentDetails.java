package com.carsgates.cr.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carsgates.cr.R;

import java.util.ArrayList;
import java.util.Calendar;

public class PaymentDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView img;
    TextView txt_booksummary,txt_terms;
    EditText et_cardName,et_cardNumber,et_Cvv;
    Spinner sp_month,sp_year;
    ArrayList<String> years,month;
    int thisyear,thismonth;
    ArrayAdapter<String> arrayAdapter,arrayAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        img= (ImageView) findViewById(R.id.img);
        Glide.with(this).load(R.raw.ic_gif).into(img);
        et_cardName= (EditText) findViewById(R.id.et_cardholdername);
        et_cardNumber=(EditText) findViewById(R.id.et_cardnumber);
        et_Cvv= (EditText) findViewById(R.id.et_cvv);
        txt_terms= (TextView)findViewById(R.id.txt_terms);
        txt_booksummary=(TextView)findViewById(R.id.txt_booksummary);
        sp_month= (Spinner) findViewById(R.id.sp_month);
        sp_year= (Spinner) findViewById(R.id.sp_year);
        txt_terms.setOnClickListener(this);
        txt_booksummary.setOnClickListener(this);
        setuptoolbar();
        setupSpinners();
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("Payment Details");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }

    private void setupSpinners() {
        years=new ArrayList<>();
        month=new ArrayList<>();
        thisyear= Calendar.getInstance().get(Calendar.YEAR);
        thismonth=1;
        for(int i=2050;i>=thisyear;i--)
        {
            years.add(String.valueOf(i));
        }
        for (int j=12;j>=thismonth;j--)
        {
            month.add(String.valueOf(j));
        }
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,years);
        arrayAdapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,month);
        sp_year.setAdapter(arrayAdapter);
        sp_month.setAdapter(arrayAdapter1);
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.bottomToolBar);
        TextView textView= (TextView) toolbar.findViewById(R.id.txt_bot);
        textView.setText("Book Now-100% Secure");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(PaymentDetails.this, ThankYou.class));
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
        switch (v.getId())
        {
            case R.id.txt_terms:
                startActivity(new Intent(PaymentDetails.this, TermsandCondition.class));
                break;
            case R.id.txt_booksummary:
                startActivity(new Intent(PaymentDetails.this, BookingHistory.class));
                break;

        }
    }
}
