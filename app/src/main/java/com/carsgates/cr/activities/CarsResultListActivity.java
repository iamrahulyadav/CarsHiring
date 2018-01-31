package com.carsgates.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.carsgates.cr.R;
import com.carsgates.cr.adapter.CarResultsListAdapter;

import com.carsgates.cr.webservices.CarDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CarsResultListActivity extends AppBaseActivity {

    public String filter ;
    List<CarDetails> listCarResult =  new ArrayList<>();
    CarResultsListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_result_list);

        filter =  getResources().getString(R.string.recommended);

        actionBar = getSupportActionBar() ;
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        CarDetails car_list = new CarDetails();
        car_list.setModel_name("FORD FIGO ");
        car_list.setCarmanagement_price(" 200000");
        car_list.setCarmanagement_carimage("t_ECAR_AE.jpg");
        car_list.setCompany_logo("logo8.png");
        listCarResult.add(car_list);
//        listCarResult = (List<CarDetails>) getIntent().getSerializableExtra("car_list") ;
        RecyclerView recycler_search_cars = (RecyclerView) findViewById(R.id.recycler_search_cars);
         listAdapter = new CarResultsListAdapter(this,listCarResult, new CarResultsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarDetails carDetail) {
                Intent intent = new Intent(CarsResultListActivity.this,CarDetail.class);
                intent.putExtra("car_id",carDetail.carmanagement_id);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_search_cars.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recycler_search_cars.addItemDecoration(itemDecoration);

        recycler_search_cars.setAdapter(listAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(getResources().getString(R.string.car_results));
    }

    public void openSelectionSortedBy(View view) {
        Intent intent = new Intent(CarsResultListActivity.this,SortingSelectionActivity.class);
        intent.putExtra("filter",filter) ;
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== 200){
            if(resultCode== RESULT_OK){
                filter = data.getStringExtra("filter");
                getShortedData() ;
            }
        }
        if(requestCode== 201){
            if(resultCode== RESULT_OK)
            {

            }
        }
    }

    private void getShortedData() {
        Collections.sort(listCarResult, new Comparator<CarDetails>() {
            @Override
            public int compare(CarDetails o1, CarDetails o2) {
                int result = 0 ;
                switch(filter){
                    case "Recommended" :

                        break ;
                    case "Price (Low to High)" :
                        result = (int)(Double.parseDouble(o1.carmanagement_price)-Double.parseDouble(o2.carmanagement_price)+1);
                        break ;

                    case "Price (High to Low)" :
                        result = (int)(Double.parseDouble(o2.carmanagement_price)-Double.parseDouble(o1.carmanagement_price));
                        break ;

                    case "Rating" :

                        break ;
                }
                return result;
            }
        });
        listAdapter.notifyDataSetChanged();
    }

    public void openSelectionFilter(View view) {
        Intent intent = new Intent(CarsResultListActivity.this,SelectFilterActivity.class);
        startActivityForResult(intent,201);
    }

}
