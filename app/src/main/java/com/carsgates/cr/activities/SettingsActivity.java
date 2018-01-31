package com.carsgates.cr.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.mukesh.tinydb.TinyDB;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * Created by Atul Kumar Gupta on 5/3/2017.
 * Contact Number : +91 8470967433
 */

public class SettingsActivity extends AppBaseActivity implements SeekBar.OnSeekBarChangeListener {
    SeekBar seekBar;
    TextView seektext;
    String value;
    private int miles;
    private TinyDB sherprf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sherprf = new TinyDB(getApplicationContext());
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        miles = sherprf.getInt("miles") ;
        seektext= (TextView) findViewById(R.id.txt_seek);
        seekBar= (SeekBar) findViewById(R.id.seekbar);
        seektext.setText(String.valueOf(miles));

        seekBar.setProgress(miles);
        seekBar.setOnSeekBarChangeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(getResources().getString(R.string.action_settings));

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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        value= String.valueOf(progress);
        sherprf.putInt("miles",progress);
        seektext.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
