package com.carsgates.cr.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.carsgates.cr.adapter.FilterValRecyclerAdapter;
import com.carsgates.cr.models.FilterDefaultMultipleListModel;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectFilterActivity extends AppBaseActivity implements View.OnClickListener, FilterValRecyclerAdapter.OnClickItem {
    CheckBox supp;
    private FilterValRecyclerAdapter filterValAdapter;
    CheckedTextView sup,air,manual,seats,doors,fuel;
    Button reset,applyfilter;
    RecyclerView rec_supplier,recy_package,recy_carfeatures,recy_insurance;
    private ArrayList<String> sizes = new ArrayList<>();
    private ArrayList<FilterDefaultMultipleListModel> sizeMultipleListModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_filter);
        rec_supplier=findViewById(R.id.rec_supplier);
        recy_package=findViewById(R.id.recy_package);
        recy_carfeatures=findViewById(R.id.recy_carfeatures);

        recy_insurance=findViewById(R.id.recy_insurance);
        sizes = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.filter_size)));
        for (String size:sizes)
        {
            FilterDefaultMultipleListModel model = new FilterDefaultMultipleListModel();
            model.setName(size);
            sizeMultipleListModels.add(model);

        }
        filterValAdapter=new FilterValRecyclerAdapter(this,R.layout.filter_list_val_item_layout,sizeMultipleListModels);
        rec_supplier.setAdapter(filterValAdapter);
        rec_supplier.setLayoutManager(new LinearLayoutManager(this));
        rec_supplier.setHasFixedSize(true);
        setuptoolbar();

        filterValAdapter.setonclick(this);
        //Buttons
        reset= (Button) findViewById(R.id.reset);
        applyfilter= (Button) findViewById(R.id.apply_filter);

        reset.setOnClickListener(this);
        applyfilter.setOnClickListener(this);

        setSelectedCarTypes();
    }

    private void setuptoolbar() {
        actionBar = getSupportActionBar() ;
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
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

            case R.id.reset:

            case R.id.apply_filter:
                finish();
                break;
        }

    }


    @Override
    public void itemclick(View v, int i) {
        filterlistclick(i);
    }

    private void filterlistclick(int i) {
        filterValAdapter.setitemselected(i);
    }
}
