package com.carsgates.cr.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;

public class SelectFilterActivity extends AppBaseActivity implements View.OnClickListener {
    CheckBox supp;
    CheckedTextView sup,air,manual,seats,doors,fuel;
    Button reset,applyfilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_filter);
        actionBar = getSupportActionBar() ;
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        //ChechkedTextView
        sup= (CheckedTextView) findViewById(R.id.supplier);
        air= (CheckedTextView) findViewById(R.id.filt_air);
        manual= (CheckedTextView) findViewById(R.id.filt_manual);
        seats= (CheckedTextView) findViewById(R.id.filt_seaat);
        doors= (CheckedTextView) findViewById(R.id.filt_door);
        fuel= (CheckedTextView) findViewById(R.id.filt_fuel);

        sup.setOnClickListener(this);
        air.setOnClickListener(this);
        manual.setOnClickListener(this);
        seats.setOnClickListener(this);
        doors.setOnClickListener(this);
        fuel.setOnClickListener(this);

        //Buttons
        reset= (Button) findViewById(R.id.reset);
        applyfilter= (Button) findViewById(R.id.apply_filter);

        reset.setOnClickListener(this);
        applyfilter.setOnClickListener(this);

        setSelectedCarTypes();
    }

    private void setSelectedCarTypes() {

       LinearLayout carTypeContainer = (LinearLayout) findViewById(R.id.carTypeContainer) ;
       int catTypeCount  =  carTypeContainer.getChildCount() ;
        for (int indexChild = 0; indexChild < catTypeCount; indexChild++) {
           TextView carTypeTV = (TextView) carTypeContainer.getChildAt(indexChild) ;
            if(indexChild==0){
                carTypeTV.setSelected(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(getResources().getString(R.string.filter));
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
            case R.id.supplier:
                changeicon(sup);
                break;
            case R.id.filt_air:
                changeicon(air);
                break;
            case R.id.filt_manual:
                changeicon(manual);
                break;
            case R.id.filt_seaat:
                changeicon(seats);

                break;
            case R.id.filt_door:
                changeicon(doors);
                break;
            case R.id.filt_fuel:
                changeicon(fuel);
                break;
            case R.id.reset:
                sup.setChecked(true);
                sup.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                air.setChecked(true);
                air.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                manual.setChecked(true);
                manual.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                seats.setChecked(true);
                seats.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                doors.setChecked(true);
                doors.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                fuel.setChecked(true);
                fuel.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                break;
            case R.id.apply_filter:
                finish();
                break;
        }

    }

    private void changeicon(CheckedTextView v) {
        if (v.isChecked())
        {
            v.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
           v.setChecked(false);
         // Toast.makeText(getApplicationContext(),"CH",Toast.LENGTH_LONG).show();
        }
        else
        {
            v.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
            v.setChecked(true);
          //Toast.makeText(getApplicationContext(),"UH",Toast.LENGTH_LONG).show();
        }
    }

}
