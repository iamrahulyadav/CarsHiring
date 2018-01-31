package com.carsgates.cr.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carsgates.cr.R;

public class BookingHistory extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    ImageView imag;
    TextView terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        imag= (ImageView) findViewById(R.id.img);
        terms= (TextView) findViewById(R.id.txt_terms);
        terms.setOnClickListener(this);
        //  GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.ic_gif).into(imag);
        actionBar=getSupportActionBar();
        if (actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Booking Summary");
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                onBackPressed();
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_terms:
                startActivity(new Intent(BookingHistory.this,TermsandCondition.class));
                break;
        }
    }
}
