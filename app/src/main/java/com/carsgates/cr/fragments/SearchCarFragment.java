package com.carsgates.cr.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.activities.AppBaseActivity;
import com.carsgates.cr.activities.CarDetail;
import com.carsgates.cr.activities.CarsResultListActivity;
import com.carsgates.cr.activities.LocationSelectionActivity;
import com.carsgates.cr.activities.MainActivity;
import com.carsgates.cr.activities.SearchByMapActivity;
import com.carsgates.cr.activities.SearchQuery;
import com.carsgates.cr.models.SearchData;
import com.carsgates.cr.webservices.ApiResponse;
import com.carsgates.cr.webservices.CarDetails;
import com.carsgates.cr.webservices.Location;
import com.carsgates.cr.webservices.RetroFitApis;
import com.carsgates.cr.webservices.RetrofitApiBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import in.technobuff.helper.utils.PermissionRequestHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by lenovo on 3/30/2017.
 */

public class SearchCarFragment extends BaseFragment implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_PICKUP_LOCATION = 500;
    private static final int REQUEST_DESTINATION_LOCATION = 501;
    private static final int REQUEST_BY_MAP_LOCATION = 502;
    private static final int REQUEST_LOCATION_PERMISSION = 2;
    Location location = new Location();
    EditText et_return_location, et_pickup_location, et_driver_age;

    MainActivity activity;
    SearchQuery searchQuery;
    SwitchCompat switchSameDestLocation,switchDriverAge;
    private GoogleApiClient mgoogleApiclient;
    CheckBox chkUseCurrentLocation;
    TinyDB tinyDB ;
    private String token ;
    Calendar calendar_pick, calendar_drop;

    static String cityName,pickup_loc_id,drop_loc_id, dropName;

    int useCurrentLocation = 0;
    int useSameDestLocation = 0;
    int isBetweenDriverAge = 0 ;
    int pick_hours,pick_minutes,drop_hours, drop_minutes;
    private double currentLat,currentLng;
    String driver_age ,pick_date , drop_date, languagecode ,drop_hour,drop_minute,pick_minute,pick_hour,
            location_code,location_iata,location_type,location_code_drop,location_iata_drop,location_type_drop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        tinyDB = new TinyDB(getActivity().getApplicationContext());
        token = tinyDB.getString("access_token");
        languagecode = tinyDB.getString("language_id");
        et_return_location = (EditText) view.findViewById(R.id.et_return_location);
        et_return_location.setOnClickListener(this);
        et_pickup_location = (EditText) view.findViewById(R.id.et_pickup_location);
        et_pickup_location.setOnClickListener(this);

        et_driver_age = (EditText) view.findViewById(R.id.et_driver_age);

        final TextView btn_search_car = (TextView) view.findViewById(R.id.btn_search_car);
        btn_search_car.setOnClickListener(this);

        switchSameDestLocation = (SwitchCompat) view.findViewById(R.id.switchSameDestLocation);
        switchSameDestLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    et_return_location.setVisibility(View.GONE);
                    if (pickup_loc_id != null && !pickup_loc_id.isEmpty())
                        drop_loc_id = pickup_loc_id;
                } else {
                    drop_loc_id = null ;
                    et_return_location.setVisibility(View.VISIBLE);
                }
            }
        });

        switchDriverAge = (SwitchCompat) view.findViewById(R.id.switchDriverAge);
        switchDriverAge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    et_driver_age.setVisibility(View.GONE);
                } else {
                    et_driver_age.setVisibility(View.VISIBLE);
                }

            }
        });


        final LinearLayout dt_picker_journey = (LinearLayout) view.findViewById(R.id.dt_picker_journey);

        final TextView tvJourneyDatePicker = (TextView) dt_picker_journey.findViewById(R.id.tvDateDayValue);
        tvJourneyDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataPicker("journey");
            }
        });
        final TextView tvJourneyFullTimePicker = (TextView) dt_picker_journey.findViewById(R.id.tvFullTime);
        tvJourneyFullTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker("journey");
            }
        });
        final LinearLayout dt_picker_returning = (LinearLayout) view.findViewById(R.id.dt_picker_returning);
        final TextView tvReturningDatePicker = (TextView) dt_picker_returning.findViewById(R.id.tvDateDayValue);
        tvReturningDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataPicker("returning");
            }
        });
        final TextView tvReturningFullTimePicker = (TextView) dt_picker_returning.findViewById(R.id.tvFullTime);
        tvReturningFullTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker("returning");
            }
        });


        chkUseCurrentLocation = (CheckBox) view.findViewById(R.id.chkUseCurrentLocation);
        chkUseCurrentLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_pickup_location.setText("");
                    et_pickup_location.setEnabled(false);
                    if (Utility.checkGooglePlayService(getActivity()))
                        setupLocation();
                }else{
                    et_pickup_location.setEnabled(true);
                }
            }
        });

        return view;
    }


    private void showTimePicker(final String type) {

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                bindTimeToGUI(type, i, i1);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    private void bindTimeToGUI(String type, int i, int i1) {
        String timeStr = setFullTime(i, i1);
        calendar_drop.set(calendar_drop.get(Calendar.YEAR),calendar_drop.get(Calendar.MONTH),
                calendar_drop.get(Calendar.DAY_OF_MONTH),i,i1);
        switch (type) {
            case "journey":
                pick_hours = i ;
                pick_minutes =i1 ;
                final LinearLayout dt_picker_journey = (LinearLayout) view.findViewById(R.id.dt_picker_journey);
                final TextView tvPickFullTime = (TextView) dt_picker_journey.findViewById(R.id.tvFullTime);
                tvPickFullTime.setText(getResources().getString(R.string.txtTime) +" : " + timeStr);
                calendar_pick.set(calendar_pick.get(Calendar.YEAR),calendar_pick.get(Calendar.MONTH),
                        calendar_pick.get(Calendar.DAY_OF_MONTH),i,i1);
                bindTimeToGUI("returning", calendar_drop.get(Calendar.HOUR_OF_DAY), calendar_drop.get(Calendar.MINUTE));
                break;

            case "returning":
                drop_hours = i ;
                drop_minutes = i1 ;
                final LinearLayout dt_picker_returning = (LinearLayout) view.findViewById(R.id.dt_picker_returning);
                final TextView tvDropFullTime = (TextView) dt_picker_returning.findViewById(R.id.tvFullTime);
                tvDropFullTime.setText(getResources().getString(R.string.txtTime) +" : " + timeStr);
                break;
        }
    }

    private String setFullTime(int hoursOfDay, int minutes) {

        String result;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 3, 1, hoursOfDay, minutes);

        SimpleDateFormat monthFormat = new SimpleDateFormat("h:mm a", Locale.US);
        result = monthFormat.format(calendar.getTime());

        return result;
    }


    private void showDataPicker(final String type) {
        Calendar calendar = calendar_pick;
        if(type.equals("returning")){
            calendar = calendar_drop ;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                bindDateToGUI(type, i, i1, i2);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(calendar_pick.getTimeInMillis());
        datePickerDialog.show();
    }

    private void bindDateToGUI(String type, int i, int i1, int i2) {

        String dayMon = setFullDate(i, i1, i2);
        final String dateValueString = setValueFullDate(i, i1, i2);
        switch (type) {
            case "journey":
                final LinearLayout dt_picker_journey = (LinearLayout) view.findViewById(R.id.dt_picker_journey);
                final TextView tvJourneyDatePicker = (TextView) dt_picker_journey.findViewById(R.id.tvDateDayValue);
                final TextView tvJourneyDateDayNameWithMonthName = (TextView) dt_picker_journey.findViewById(R.id.tvDateDayNameWithMonthName);
                tvJourneyDatePicker.setText(String.valueOf(i2));
                tvJourneyDateDayNameWithMonthName.setText(dayMon);
                pick_date = dateValueString;
                calendar_pick.set(i,i1,i2);
                calendar_drop  = (Calendar) calendar_pick.clone();
                calendar_drop.add(Calendar.DATE,3);
                bindDateToGUI("returning", calendar_drop.get(Calendar.YEAR), calendar_drop.get(Calendar.MONTH), calendar_drop.get(Calendar.DAY_OF_MONTH));
                break;

            case "returning":
                final LinearLayout dt_picker_returning = (LinearLayout) view.findViewById(R.id.dt_picker_returning);
                final TextView tvReturningDatePicker = (TextView) dt_picker_returning.findViewById(R.id.tvDateDayValue);
                final TextView tvReturningDateDayNameWithMonthName = (TextView) dt_picker_returning.findViewById(R.id.tvDateDayNameWithMonthName);
                tvReturningDatePicker.setText(String.valueOf(i2));
                tvReturningDateDayNameWithMonthName.setText(dayMon);
                drop_date = dateValueString ;

                calendar_drop.set(i,i1,i2);
                break;
        }

    }

    private String setValueFullDate(int year, int month, int day) {
        String result;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        result = monthFormat.format(calendar.getTime());
        return result;
    }

    private String setFullDate(int year, int month, int day) {
        String result;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat monthFormat = new SimpleDateFormat("EEE | MMMM", Locale.US);
        result = monthFormat.format(calendar.getTime());
        return result;
    }

    @Override
    public void setDefaultSettings() {

        activity = (MainActivity) getActivity();
        searchQuery = activity.searchQuery;

        et_driver_age.setVisibility(View.GONE);
        et_return_location.setVisibility(View.GONE);
        et_return_location.setInputType(InputType.TYPE_NULL);
        et_return_location.setFocusable(false);

        et_pickup_location.setInputType(InputType.TYPE_NULL);
        et_pickup_location.setFocusable(false);

        switchSameDestLocation = (SwitchCompat) view.findViewById(R.id.switchSameDestLocation);
        switchSameDestLocation.setChecked(searchQuery.isDestAsPickup);

        switchDriverAge = (SwitchCompat) view.findViewById(R.id.switchDriverAge);
        switchDriverAge.setChecked(searchQuery.isDriverAged);

        final SwitchCompat switchSearchByMap = (SwitchCompat) view.findViewById(R.id.switchSearchByMap);
        switchSearchByMap.setChecked(searchQuery.isSearchByMap);

        calendar_pick = Calendar.getInstance();
        bindDateToGUI("journey", calendar_pick.get(Calendar.YEAR), calendar_pick.get(Calendar.MONTH), calendar_pick.get(Calendar.DAY_OF_MONTH));
        bindTimeToGUI("journey", calendar_pick.get(Calendar.HOUR_OF_DAY), calendar_pick.get(Calendar.MINUTE));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = ((MainActivity) getActivity()).toolbar;
        toolbar.setTitle(getResources().getString(R.string.action_search_car));
        et_pickup_location.setText(cityName);
        et_return_location.setText(dropName);
        checkGPSStatus();
    }

    public void setLanguages(String language_code){
        Locale locale = new Locale(language_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config,
                getActivity().getResources().getDisplayMetrics());
    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Preserve Data
        switchSameDestLocation = (SwitchCompat) view.findViewById(R.id.switchSameDestLocation);
        searchQuery.isDestAsPickup = switchSameDestLocation.isChecked();

        switchDriverAge = (SwitchCompat) view.findViewById(R.id.switchDriverAge);
        searchQuery.isDriverAged = switchDriverAge.isChecked();

        final SwitchCompat switchSearchByMap = (SwitchCompat) view.findViewById(R.id.switchSearchByMap);
        searchQuery.isSearchByMap = switchSearchByMap.isChecked();

        activity.searchQuery = searchQuery;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_pickup_location:
                startActivityForResult(new Intent(getActivity(), LocationSelectionActivity.class), REQUEST_PICKUP_LOCATION);
                break;

            case R.id.et_return_location:
                startActivityForResult(new Intent(getActivity(), LocationSelectionActivity.class), REQUEST_DESTINATION_LOCATION);
                break;

            case R.id.btn_search_car:
                requestForSearchCar() ;
//                Intent intent = new Intent(getActivity(), CarsResultListActivity.class);
//                startActivity(intent);
        }
    }

    private void chooseSearchAction(List<SearchData> car_list) {

        final SwitchCompat switchSearchByMap = (SwitchCompat) view.findViewById(R.id.switchSearchByMap);
        Intent intent ;
        if (switchSearchByMap.isChecked()) {
            intent = new Intent(getActivity(), SearchByMapActivity.class);
            intent.putExtra("car_list", (Serializable) car_list) ;
            startActivity(intent);
        } else {
            intent = new Intent(getActivity(), CarsResultListActivity.class);
            intent.putExtra("car_list", (Serializable) car_list) ;
            startActivity(intent);
        }
    }
    public static List<SearchData>searchData = new ArrayList<>();


    private void requestForSearchCar() {

        /*if(!validateData()){
            return ;
        }*/

        Utility.showLoading(getActivity(),"Searching cars...");
        final SearchCarFragment _this = SearchCarFragment.this ;
        RetroFitApis retroFitApis = RetrofitApiBuilder.getRetrofitGlobal() ;

        pick_hour=String.valueOf(pick_hours>9?pick_hours:"0"+pick_hours);
        pick_minute=String.valueOf(pick_minutes>9?pick_minutes:"0"+pick_minutes);

        drop_minute=String.valueOf(drop_minutes>9?drop_minutes:"0"+drop_minutes);
        drop_hour=String.valueOf(drop_hours>9?drop_hours:"0"+drop_hours);

        Log.d("Request Data","access_token="+token+"&pick_city="+pickup_loc_id+"&pick_date="+pick_date+
                "&pick_houre="+pick_hour+"&pick_minute="+pick_minute+"&drop_city="+drop_loc_id+"&drop_date="+drop_date+"" +
                "&drop_houre="+drop_hour+"&drop_minute="+drop_minute+"&driver_age="+driver_age +
                "&use_current_location="+useCurrentLocation+"&sameas_pick_location="+useSameDestLocation +
                "&between_driver_age="+isBetweenDriverAge +"&lat="+currentLat+"&long="+currentLng);

        /*access_token=4ca2a0341708ab9f74164f6e6b75f8d63825c80d&pick_city=1&pick_date=2018-02-08&pick_houre
        =10&pick_minute=15&drop_city=1&drop_date=2018-02-10&drop_houre=10&drop_minute=15&driver_age
        =&use_current_location=0&sameas_pick_location=1&between_driver_age=1&lat=28.5929202&long=77.3163672&location_code
        =4339&location_iata
        =TXL&location_type=a&location_code_drop=4339&location_iata_drop=TXL&location_type_drop=a&language_code=en*/
        pickup_loc_id = "1";
        token="4ca2a0341708ab9f74164f6e6b75f8d63825c80d";
        pick_date ="2018-02-08";
        pick_hour ="10";
        pick_minute="15";
        drop_loc_id = "1";
        drop_date="2018-02-10";
        drop_hour ="10";
        drop_minute="15";
        driver_age="1";
        useCurrentLocation=0;
        useSameDestLocation=1;
        isBetweenDriverAge=1;
        currentLat=28.5929202;
        currentLng=77.3163672;
        location_code="4339";
        location_iata="TXL";
        location_type="a";
        location_code_drop="4339";
        location_iata_drop="TXL";
        location_type_drop="a";
        languagecode = "en";

        Call<ApiResponse> responseCall = retroFitApis.search("4ca2a0341708ab9f74164f6e6b75f8d63825c80d","1",
                "2018-02-08","10",
                "15","1","2018-02-10","10","15","",useCurrentLocation,
                useSameDestLocation,1,currentLat,currentLng,location_code,location_iata,
                location_type,location_code_drop,location_iata_drop,location_type_drop,languagecode) ;
        final Gson gson = new Gson();
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Utility.hidepopup();

                if(response.body()!=null){

                    if(response.body().status){
                        searchData=response.body().response.car_list;

                        String data = gson.toJson(searchData);
                        Log.d(TAG, "onResponse: "+data);
                        for (SearchData searchData1 :searchData){
                            Log.d(TAG, "onResponse: "+searchData1.getDeposit_name());
                        }
                        ArrayList<SearchData>searchData1 = new ArrayList<>();
                        searchData1.addAll(searchData);

//                        chooseSearchAction(data);

//                        for testing
                        Intent  intent = new Intent(getActivity(), CarsResultListActivity.class);
                        startActivity(intent);

                    }else{
                        if(response.body().error_code==102)
                            ((AppBaseActivity)getActivity()).getToken(_this);
                    }
                }
                Toast.makeText(getActivity(), response.body().msg, Toast.LENGTH_SHORT).show();


//                for test data
          /*      Intent intent = new Intent(getActivity(), CarsResultListActivity.class);

//                intent.putExtra("car_list", (Serializable) car_list) ;
                startActivity(intent);*/
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Utility.hidepopup();
                Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            location = (Location) data.getSerializableExtra(LocationSelectionActivity.RESPONSE_DATA);
        }
        if (resultCode == 0) {
            if (requestCode == REQUEST_BY_MAP_LOCATION) {

            }
        } else if (resultCode == LocationSelectionActivity.RESPONSE_LOCATION) {
            if (requestCode == REQUEST_PICKUP_LOCATION) {
                cityName = location.city_name;
                pickup_loc_id = location.city_id;

                if (switchSameDestLocation.isChecked()) {
                    drop_loc_id = pickup_loc_id;
                }
            } else if (requestCode == REQUEST_DESTINATION_LOCATION) {
                drop_loc_id = location.city_id;
                dropName = location.city_name;
            }
        }
    }

    protected synchronized void setupLocation() {
        if (PermissionRequestHandler.requestPermissionToLocation(getActivity(),this)) {
            checkGPSStatus();
            mgoogleApiclient = new GoogleApiClient.Builder(getActivity()).
                    addConnectionCallbacks(this).
                    addOnConnectionFailedListener(this).
                    addApi(LocationServices.API).build();

            mgoogleApiclient.connect();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        android.location.Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mgoogleApiclient);
        if (mLocation != null) {
            currentLat = mLocation.getLatitude();
            currentLng = mLocation.getLongitude();
            Log.d("Current Locations",currentLat+" , "+currentLng);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                    setupLocation();
                } else {
                    chkUseCurrentLocation.setChecked(false);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private boolean validateData(){

        // Location
        if(!chkUseCurrentLocation.isChecked()){
            useCurrentLocation = 0 ;
            currentLng = 0.0;
            currentLat = 0.0 ;
            // pickup_loc_id
            if(pickup_loc_id==null || pickup_loc_id.trim().isEmpty()){
                Toast.makeText(activity, "Please select Pickup location.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(switchSameDestLocation.isChecked()){
                useSameDestLocation = 1 ;
                drop_loc_id = pickup_loc_id ;
            }else {
                if(drop_loc_id==null || drop_loc_id.trim().isEmpty()){
                    Toast.makeText(activity, "Please select drop location.", Toast.LENGTH_SHORT).show();
                    return false ;
                }
            }

        }else{
            useCurrentLocation = 1 ;
            //currentLat
            // currentLng
            pickup_loc_id = "" ;
            if(!switchSameDestLocation.isChecked()) {
                // drop_loc_id
                if(drop_loc_id==null || drop_loc_id.trim().isEmpty()){
                    Toast.makeText(activity, "Please select drop location.", Toast.LENGTH_SHORT).show();
                    return false ;
                }
            }else{
                drop_loc_id= "";
                useSameDestLocation = 1 ;
            }

        }
        // Date Time
        Log.d("Calender ",calendar_pick.getTime()+" "+calendar_drop.getTime());
        Log.d("Calender ",calendar_drop.compareTo(calendar_pick)+"");

        if(calendar_drop.compareTo(calendar_pick) <= 0){
            Toast.makeText(activity, "Please select valid drop date.", Toast.LENGTH_SHORT).show();
            return false ;
        }

        // Age
        if(switchDriverAge.isChecked()) {
            isBetweenDriverAge = 1 ;
            driver_age =  "";
        }else{
            isBetweenDriverAge= 0 ;
            driver_age = et_driver_age.getText().toString().trim();
            if(driver_age.isEmpty()){
                Toast.makeText(activity, "Please enter driver age.", Toast.LENGTH_SHORT).show();
                return false ;
            }
        }
        return true ;
    }

    @Override
    public void refreshTokenCallBack() {
        token =  tinyDB.getString("access_token") ;
        requestForSearchCar() ;
    }
}

