package com.carsgates.cr.webservices;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 14/7/17.
 */

public class CarDetails implements Serializable{

    public String carmanagement_id ;
    public String carmanagement_company ;
    public String carmanagement_company_branch ;
    public String carmanagement_cartype ;
    public String carmanagement_carbrand ;
    public String carmanagement_carmodel ;
    public String carmanagement_carname ;
    public String carmanagement_carqty ;
    public String carmanagement_price ;

    public List<CarOtherApplication> carmanagement_othspecifications ;
    public List<CarSpecification> carmanagement_specifications ;

    public String carmanagement_featuredcars ;
    public String carmanagement_carimage ;
    public String carmanagement_language_id ;
    public String carmanagement_status ;
    public String company_id ;
    public String company_name ;
    public String company_logo ;
    public String company_city ;
    public String branch_id ;
    public String model_name ;
    public String carbrandname_name ;
    public String category_name ;
    public double branch_lat  ;
    public double branch_long = 79.101563 ;

    public String getCarmanagement_id() {
        return carmanagement_id;
    }

    public void setCarmanagement_id(String carmanagement_id) {
        this.carmanagement_id = carmanagement_id;
    }

    public String getCarmanagement_company() {
        return carmanagement_company;
    }

    public void setCarmanagement_company(String carmanagement_company) {
        this.carmanagement_company = carmanagement_company;
    }

    public String getCarmanagement_company_branch() {
        return carmanagement_company_branch;
    }

    public void setCarmanagement_company_branch(String carmanagement_company_branch) {
        this.carmanagement_company_branch = carmanagement_company_branch;
    }

    public String getCarmanagement_cartype() {
        return carmanagement_cartype;
    }

    public void setCarmanagement_cartype(String carmanagement_cartype) {
        this.carmanagement_cartype = carmanagement_cartype;
    }

    public String getCarmanagement_carbrand() {
        return carmanagement_carbrand;
    }

    public void setCarmanagement_carbrand(String carmanagement_carbrand) {
        this.carmanagement_carbrand = carmanagement_carbrand;
    }

    public String getCarmanagement_carmodel() {
        return carmanagement_carmodel;
    }

    public void setCarmanagement_carmodel(String carmanagement_carmodel) {
        this.carmanagement_carmodel = carmanagement_carmodel;
    }

    public String getCarmanagement_carname() {
        return carmanagement_carname;
    }

    public void setCarmanagement_carname(String carmanagement_carname) {
        this.carmanagement_carname = carmanagement_carname;
    }

    public String getCarmanagement_carqty() {
        return carmanagement_carqty;
    }

    public void setCarmanagement_carqty(String carmanagement_carqty) {
        this.carmanagement_carqty = carmanagement_carqty;
    }

    public String getCarmanagement_price() {
        return carmanagement_price;
    }

    public void setCarmanagement_price(String carmanagement_price) {
        this.carmanagement_price = carmanagement_price;
    }

    public List<CarOtherApplication> getCarmanagement_othspecifications() {
        return carmanagement_othspecifications;
    }

    public void setCarmanagement_othspecifications(List<CarOtherApplication> carmanagement_othspecifications) {
        this.carmanagement_othspecifications = carmanagement_othspecifications;
    }

    public List<CarSpecification> getCarmanagement_specifications() {
        return carmanagement_specifications;
    }

    public void setCarmanagement_specifications(List<CarSpecification> carmanagement_specifications) {
        this.carmanagement_specifications = carmanagement_specifications;
    }

    public String getCarmanagement_featuredcars() {
        return carmanagement_featuredcars;
    }

    public void setCarmanagement_featuredcars(String carmanagement_featuredcars) {
        this.carmanagement_featuredcars = carmanagement_featuredcars;
    }

    public String getCarmanagement_carimage() {
        return carmanagement_carimage;
    }

    public void setCarmanagement_carimage(String carmanagement_carimage) {
        this.carmanagement_carimage = carmanagement_carimage;
    }

    public String getCarmanagement_language_id() {
        return carmanagement_language_id;
    }

    public void setCarmanagement_language_id(String carmanagement_language_id) {
        this.carmanagement_language_id = carmanagement_language_id;
    }

    public String getCarmanagement_status() {
        return carmanagement_status;
    }

    public void setCarmanagement_status(String carmanagement_status) {
        this.carmanagement_status = carmanagement_status;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getCompany_city() {
        return company_city;
    }

    public void setCompany_city(String company_city) {
        this.company_city = company_city;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCarbrandname_name() {
        return carbrandname_name;
    }

    public void setCarbrandname_name(String carbrandname_name) {
        this.carbrandname_name = carbrandname_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public double getBranch_lat() {
        return branch_lat;
    }

    public void setBranch_lat(double branch_lat) {
        this.branch_lat = branch_lat;
    }

    public double getBranch_long() {
        return branch_long;
    }

    public void setBranch_long(double branch_long) {
        this.branch_long = branch_long;
    }
}
