package com.carsgates.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.carsgates.cr.R;
import com.carsgates.cr.adapter.CarResultsListAdapter;

import com.carsgates.cr.fragments.SearchCarFragment;
import com.carsgates.cr.models.SearchData;
import com.carsgates.cr.webservices.CarDetails;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarsResultListActivity extends AppBaseActivity {
    Gson gson = new Gson();
    public String filter ;
    List<SearchData> listCarResult =  new ArrayList<>();
    List<SearchData.FeatureBean> featuresAllList =  new ArrayList<>();
    public static List<String> supplierList=new ArrayList<>();
    List<String>featuresList=new ArrayList<>();
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


        listCarResult = SearchCarFragment.searchData;
//        get supplier

        for (SearchData searchData : listCarResult){
            supplierList.add(searchData.getSupplier());
           featuresAllList.add(searchData.getFeature());
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(supplierList);
        supplierList.clear();
        supplierList.addAll(hs);

/*
        for (SearchData.FeatureBean featureBean: featuresAllList){
            featuresList.add(featureBean.getAircondition());
            if (featureBean.getTransmission().equals("Automatic")){
                featuresList.add(featureBean.getTransmission());
            }
            featuresList.add("4+ Door");

        }*/

        RecyclerView recycler_search_cars = (RecyclerView) findViewById(R.id.recycler_search_cars);
         listAdapter = new CarResultsListAdapter(this,listCarResult, new CarResultsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchData carDetail) {
                Intent intent = new Intent(CarsResultListActivity.this,CarDetail.class);
//              intent.putExtra("car_id",carDetail.get);
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
        Collections.sort(listCarResult, new Comparator<SearchData>() {
            @Override
            public int compare(SearchData o1, SearchData o2) {
                int result = 0 ;
                switch(filter){
                    case "Recommended" :

                        break ;
                  /*  case "Price (Low to High)" :
                        result = (int)(Double.parseDouble(o1.getCar_list())-Double.parseDouble(o2.carmanagement_price)+1);
                        break ;

                    case "Price (High to Low)" :
                        result = (int)(Double.parseDouble(o2.carmanagement_price)-Double.parseDouble(o1.carmanagement_price));
                        break ;
*/
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
