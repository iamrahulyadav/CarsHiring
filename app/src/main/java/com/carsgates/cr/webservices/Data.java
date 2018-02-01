package com.carsgates.cr.webservices;

import com.carsgates.cr.models.BookingData;
import com.carsgates.cr.models.SearchData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/7/17.
 */

public class Data {
    public AboutUs about_us;
    public ContactUs contact_us ;
    public CarDetails car_detail ;
    public UserDetails userdetail;
    public Driver driver_detail ;
    public SingleCarDetails singleCarDetails;
    public List<Location> locations_list;
    public List<SearchData> car_list ;
    public List<Driver> driver_list ;
    public ArrayList<LanguageModel> language_list;
    public ArrayList<CardList> card_list;
    public List<Protection> protection ;
    public List<BookingData>booking_detail;


}
