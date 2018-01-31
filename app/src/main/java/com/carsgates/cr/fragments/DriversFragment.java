package com.carsgates.cr.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.AppGlobal;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.activities.AddDriverActivity;
import com.carsgates.cr.adapter.DriverAdapter;
import com.carsgates.cr.models.DriverModel;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.Driver;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sony on 02-05-2017.
 */

public class DriversFragment extends Fragment implements DriverAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {
    AppGlobal appGlobal=AppGlobal.getInstancess();
    View view,v;
    RecyclerView recyclerView;
    DriverAdapter driverAdapter;
    List<DriverModel> driverlists=new ArrayList<>();
    RippleDrawable rippleDrawable;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout rr;
    TinyDB sharedpref;
    String token,userid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_drivers_selection,container,false);
        sharedpref=new TinyDB(getContext());
        appGlobal.context=getContext();
        userid=sharedpref.getString("userid");
        token=sharedpref.getString("access_token");
        v=getActivity().findViewById(android.R.id.content);
        recyclerView= (RecyclerView) view.findViewById(R.id.rec_driver_list);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipedriver);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.redStrong));
        swipeRefreshLayout.setOnRefreshListener(this);
      /*  DefaultItemAnimator itemAnimator=new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);*/
        driverAdapter=new DriverAdapter(getActivity(),driverlists);
        RecyclerView.LayoutManager manager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        driverAdapter.setClickListene(this);
        recyclerView.setAdapter(driverAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        setuptoolbar();
        return view;
    }

    private void preapredata() {
        if(driverlists!=null)
        {
            driverlists.clear();
        }
        else
        {
            Utility.message(getContext(),"No record Found");
        }
        Utility.showloadingPopup(getActivity());
        RetroFitApis fitApis= RetrofitApiBuilder.getRetrofitGlobal();
        Call<ApiResponse> responseCall=fitApis.driver_list(token,userid);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Utility.hidepopup();
                if(response.body().status==true)
                {
                    List<Driver> driverList=response.body().response.driver_list;
                    if(!driverList.isEmpty()) {
                        for (int i = 0; i < driverList.size(); i++) {
                            DriverModel driverModel=new DriverModel();
                            Driver  driver=driverList.get(i);
                            driverModel.setDriver_fname(driver.driver_fname);
                            driverModel.setDriver_sname(driver.driver_lname);
                            driverModel.setDriver_email(driver.driver_email);
                            driverModel.setDriver_id(driver.driver_id);
                            driverModel.setDriverImg(R.drawable.ic_driver1);
                            driverModel.setDriver_phone(driver.driver_phone);
                            driverModel.setDriver_age(driver.driver_age);
                            driverModel.setDriver_title(driver.driver_title);
                            driverModel.setDriver_branch_id(driver.driver_branch_id);
                            driverModel.setDriver_carmanagement_id(driver.driver_carmanagement_id);
                            driverModel.setDriver_countrycode(driver.driver_countrycode);
                            driverModel.setDriver_address(driver.driver_address);
                            driverModel.setDriver_language_id(driver.driver_language_id);
                            driverModel.setDriver_nationalid(driver.driver_nationalid);
                            driverlists.add(driverModel);
                        }
                        driverAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Utility.message(getContext(), response.body().msg);
                    }
                }
                else
                {
                    //     Utility.message(getContext(), response.body().msg);
                    //access token is empty.
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Utility.message(getContext(),"Connection Error");
                Utility.hidepopup();
            }
        });
       /* for (int i=0;i<5;i++) {
            DriverModel model = new DriverModel("Ajay", "Ajay@mail.com", "1234567891", "26", R.drawable.ic_driver1);
            driverlist.add(model);
            model = new DriverModel("Abc", "Abc@mail.com", "15987632486", "29", R.drawable.ic_driver2);
            driverlist.add(model);
            model = new DriverModel("John", "John@mail.com", "9876543210", "25", R.drawable.ic_driver3);
            driverlist.add(model);
        }*/
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.bottomToolBar);
        TextView txt= (TextView) toolbar.findViewById(R.id.txt_bot);
        ImageView imageView= (ImageView) toolbar.findViewById(R.id.img_bot);
        imageView.setImageResource(R.drawable.ic_add);
        txt.setText("Add");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddDriverActivity.class));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        preapredata();
        Activity activity=getActivity();
    }
    @Override
    public void itemClicked(View view, int postion) {
        String name= ((TextView) view.findViewById(R.id.driver_name)).getText().toString();
        String Id= ((TextView) view.findViewById(R.id.drivers_id)).getText().toString();
        //  Drawable d=((ImageView) view.findViewById(R.id.driver_image)).getDrawable().getCurrent();
        Intent intent=new Intent(getContext(),AddDriverActivity.class);
        intent.putExtra("driverID",Id);
        startActivity(intent);
/*
        Snackbar.make(getView(),name+Id,Snackbar.LENGTH_SHORT).show();
        Toast.makeText(getContext(),name+Id,Toast.LENGTH_SHORT).show();
        ImageView imageView= ((ImageView) view.findViewById(R.id.arowimg));
        imageView.setImageResource(R.drawable.ic_white_arrow);
        imageView.setBackgroundColor(getContext().getResources().getColor(R.color.bottom_color));*/

    }

    @Override
    public void onRefresh()
    {
        Toast.makeText(getContext(),"Loading...",Toast.LENGTH_SHORT).show();
        /*driverAdapter=new DriverAdapter(getActivity(),driverlists);
        recyclerView.setAdapter(driverAdapter);
        driverAdapter.setClickListene(this);
        driverAdapter.notifyDataSetChanged();*/
        preapredata();
        swipeRefreshLayout.setRefreshing(false);
    }
}
