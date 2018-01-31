package com.carsgates.cr.models;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SpinnerItemModel {
    String txt;
    Integer imageId;
    public SpinnerItemModel(String txt, Integer imageId) {
        this.txt = txt;
        this.imageId = imageId;
    }



    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
