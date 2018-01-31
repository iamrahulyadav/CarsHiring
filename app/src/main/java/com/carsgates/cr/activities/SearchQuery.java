package com.carsgates.cr.activities;

import java.io.Serializable;

/**
 * Created by Atul Kumar Gupta on 5/3/2017.
 * Contact Number : +91 8470967433
 */

public class SearchQuery implements Serializable {
    public String pickupLocation = "" ;
    public boolean isDestAsPickup = true ;
    public boolean isDriverAged = true ;
    public boolean isSearchByMap = false ;
}