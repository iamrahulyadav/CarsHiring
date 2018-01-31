package com.carsgates.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.carsgates.cr.R;
import com.carsgates.cr.adapter.SearchByMapAdapter;
import com.carsgates.cr.webservices.CarDetails;
import com.carsgates.cr.webservices.CarSpecification;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Atul Kumar Gupta on 5/3/2017.
 * Contact Number : +91 8470967433
 */

public class SearchByMapActivity extends AppBaseActivity implements OnMapReadyCallback{

    List<CarDetails> listSearchByResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Testing","Before");
        setContentView(R.layout.activity_search_by_map);
        Log.d("Testing","After");
        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }


        listSearchByResult = (List<CarDetails>) getIntent().getSerializableExtra("car_list") ;

        RecyclerView recycler_search_by_map = (RecyclerView) findViewById(R.id.recycler_search_by_map) ;
        SearchByMapAdapter searchByMapAdapter = new SearchByMapAdapter(listSearchByResult, new SearchByMapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarDetails carDetail) {
                Intent intent = new Intent(SearchByMapActivity.this,CarDetail.class);
                intent.putExtra("car_id",carDetail.carmanagement_id);
                startActivity(intent);
            }
        }) ;
        recycler_search_by_map.setAdapter(searchByMapAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_search_by_map.setLayoutManager(layoutManager);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if(mapFragment!=null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle("Search By Map");
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
    public void onMapReady(final GoogleMap googleMap) {
        for (int indexR = 0; indexR < listSearchByResult.size(); indexR++) {
            CarDetails result =  listSearchByResult.get(indexR);
            final String car_id =  result.carmanagement_id ;
            MarkerOptions markerOptions = new MarkerOptions() ;
            LatLng latLng = new LatLng(result.branch_lat,result.branch_long);
            markerOptions.position(latLng);
            markerOptions.title(result.model_name);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_logo));
            googleMap.addMarker(markerOptions);
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(SearchByMapActivity.this,CarDetail.class);
                    intent.putExtra("car_id",car_id);
                    startActivity(intent);
                }
            });
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2));
        }


    }




}
