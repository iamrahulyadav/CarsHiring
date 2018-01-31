package com.carsgates.cr.webservices;

import java.io.Serializable;

/**
 * Created by root on 14/7/17.
 */

public class CarOtherApplication implements Serializable{

    public String ospecification_id ;
    public String ospecification_name ;
    public String ospecification_description ;

    public String getOspecification_id() {
        return ospecification_id;
    }

    public void setOspecification_id(String ospecification_id) {
        this.ospecification_id = ospecification_id;
    }

    public String getOspecification_name() {
        return ospecification_name;
    }

    public void setOspecification_name(String ospecification_name) {
        this.ospecification_name = ospecification_name;
    }

    public String getOspecification_description() {
        return ospecification_description;
    }

    public void setOspecification_description(String ospecification_description) {
        this.ospecification_description = ospecification_description;
    }
}
