package com.carsgates.cr.webservices;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ask on 11/14/2017.
 */

public class SingleCarDetails implements Serializable{


    /**
     * error_code : 101
     * status : true
     * response : {"car_detail":{"carmanagement_id":"4","carmanagement_company":"3","carmanagement_company_branch":"4","carmanagement_cartype":"0","carmanagement_carbrand":"2","carmanagement_carmodel":"11","carmanagement_carname":"","carmanagement_carqty":"5","carmanagement_price":"50.00","carmanagement_specifications":false,"carmanagement_othspecifications":false,"carmanagement_sheet":"4","carmanagement_door":"3","carmanagement_small":"4","carmanagement_large":"2","carmanagement_transmission":"manual","carmanagement_aircondition":"yes","carmanagement_year":"","carmanagement_fullpolicy":"2","carmanagement_following_free":"3","carmanagement_featuredcars":"no","carmanagement_carimage":null,"carmanagement_status":"1","company_id":"3","company_name":"Aston Martin","company_logo":"aston-martin-logo2.jpg","company_city":"19370","branch_id":"4","branch_lat":"28.6078909","branch_long":"77.3054884","model_name":"ELITE i20","carbrandname_name":"Hundai","category_id":"1","category_name":"Mini"},"protection":[{"protection_id":"1","protection_name":"A1","protection_status":"1"}]}
     */

    private int error_code;
    private boolean status;
    private ResponseBean response;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * car_detail : {"carmanagement_id":"4","carmanagement_company":"3","carmanagement_company_branch":"4","carmanagement_cartype":"0","carmanagement_carbrand":"2","carmanagement_carmodel":"11","carmanagement_carname":"","carmanagement_carqty":"5","carmanagement_price":"50.00","carmanagement_specifications":false,"carmanagement_othspecifications":false,"carmanagement_sheet":"4","carmanagement_door":"3","carmanagement_small":"4","carmanagement_large":"2","carmanagement_transmission":"manual","carmanagement_aircondition":"yes","carmanagement_year":"","carmanagement_fullpolicy":"2","carmanagement_following_free":"3","carmanagement_featuredcars":"no","carmanagement_carimage":null,"carmanagement_status":"1","company_id":"3","company_name":"Aston Martin","company_logo":"aston-martin-logo2.jpg","company_city":"19370","branch_id":"4","branch_lat":"28.6078909","branch_long":"77.3054884","model_name":"ELITE i20","carbrandname_name":"Hundai","category_id":"1","category_name":"Mini"}
         * protection : [{"protection_id":"1","protection_name":"A1","protection_status":"1"}]
         */

        private CarDetailBean car_detail;
        private List<ProtectionBean> protection;

        public CarDetailBean getCar_detail() {
            return car_detail;
        }

        public void setCar_detail(CarDetailBean car_detail) {
            this.car_detail = car_detail;
        }

        public List<ProtectionBean> getProtection() {
            return protection;
        }

        public void setProtection(List<ProtectionBean> protection) {
            this.protection = protection;
        }

        public static class CarDetailBean {
            /**
             * carmanagement_id : 4
             * carmanagement_company : 3
             * carmanagement_company_branch : 4
             * carmanagement_cartype : 0
             * carmanagement_carbrand : 2
             * carmanagement_carmodel : 11
             * carmanagement_carname :
             * carmanagement_carqty : 5
             * carmanagement_price : 50.00
             * carmanagement_specifications : false
             * carmanagement_othspecifications : false
             * carmanagement_sheet : 4
             * carmanagement_door : 3
             * carmanagement_small : 4
             * carmanagement_large : 2
             * carmanagement_transmission : manual
             * carmanagement_aircondition : yes
             * carmanagement_year :
             * carmanagement_fullpolicy : 2
             * carmanagement_following_free : 3
             * carmanagement_featuredcars : no
             * carmanagement_carimage : null
             * carmanagement_status : 1
             * company_id : 3
             * company_name : Aston Martin
             * company_logo : aston-martin-logo2.jpg
             * company_city : 19370
             * branch_id : 4
             * branch_lat : 28.6078909
             * branch_long : 77.3054884
             * model_name : ELITE i20
             * carbrandname_name : Hundai
             * category_id : 1
             * category_name : Mini
             */

            private String carmanagement_id;
            private String carmanagement_company;
            private String carmanagement_company_branch;
            private String carmanagement_cartype;
            private String carmanagement_carbrand;
            private String carmanagement_carmodel;
            private String carmanagement_carname;
            private String carmanagement_carqty;
            private String carmanagement_price;
            private boolean carmanagement_specifications;
            private boolean carmanagement_othspecifications;
            private String carmanagement_sheet;
            private String carmanagement_door;
            private String carmanagement_small;
            private String carmanagement_large;
            private String carmanagement_transmission;
            private String carmanagement_aircondition;
            private String carmanagement_year;
            private String carmanagement_fullpolicy;
            private String carmanagement_following_free;
            private String carmanagement_featuredcars;
            private Object carmanagement_carimage;
            private String carmanagement_status;
            private String company_id;
            private String company_name;
            private String company_logo;
            private String company_city;
            private String branch_id;
            private String branch_lat;
            private String branch_long;
            private String model_name;
            private String carbrandname_name;
            private String category_id;
            private String category_name;

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

            public boolean isCarmanagement_specifications() {
                return carmanagement_specifications;
            }

            public void setCarmanagement_specifications(boolean carmanagement_specifications) {
                this.carmanagement_specifications = carmanagement_specifications;
            }

            public boolean isCarmanagement_othspecifications() {
                return carmanagement_othspecifications;
            }

            public void setCarmanagement_othspecifications(boolean carmanagement_othspecifications) {
                this.carmanagement_othspecifications = carmanagement_othspecifications;
            }

            public String getCarmanagement_sheet() {
                return carmanagement_sheet;
            }

            public void setCarmanagement_sheet(String carmanagement_sheet) {
                this.carmanagement_sheet = carmanagement_sheet;
            }

            public String getCarmanagement_door() {
                return carmanagement_door;
            }

            public void setCarmanagement_door(String carmanagement_door) {
                this.carmanagement_door = carmanagement_door;
            }

            public String getCarmanagement_small() {
                return carmanagement_small;
            }

            public void setCarmanagement_small(String carmanagement_small) {
                this.carmanagement_small = carmanagement_small;
            }

            public String getCarmanagement_large() {
                return carmanagement_large;
            }

            public void setCarmanagement_large(String carmanagement_large) {
                this.carmanagement_large = carmanagement_large;
            }

            public String getCarmanagement_transmission() {
                return carmanagement_transmission;
            }

            public void setCarmanagement_transmission(String carmanagement_transmission) {
                this.carmanagement_transmission = carmanagement_transmission;
            }

            public String getCarmanagement_aircondition() {
                return carmanagement_aircondition;
            }

            public void setCarmanagement_aircondition(String carmanagement_aircondition) {
                this.carmanagement_aircondition = carmanagement_aircondition;
            }

            public String getCarmanagement_year() {
                return carmanagement_year;
            }

            public void setCarmanagement_year(String carmanagement_year) {
                this.carmanagement_year = carmanagement_year;
            }

            public String getCarmanagement_fullpolicy() {
                return carmanagement_fullpolicy;
            }

            public void setCarmanagement_fullpolicy(String carmanagement_fullpolicy) {
                this.carmanagement_fullpolicy = carmanagement_fullpolicy;
            }

            public String getCarmanagement_following_free() {
                return carmanagement_following_free;
            }

            public void setCarmanagement_following_free(String carmanagement_following_free) {
                this.carmanagement_following_free = carmanagement_following_free;
            }

            public String getCarmanagement_featuredcars() {
                return carmanagement_featuredcars;
            }

            public void setCarmanagement_featuredcars(String carmanagement_featuredcars) {
                this.carmanagement_featuredcars = carmanagement_featuredcars;
            }

            public Object getCarmanagement_carimage() {
                return carmanagement_carimage;
            }

            public void setCarmanagement_carimage(Object carmanagement_carimage) {
                this.carmanagement_carimage = carmanagement_carimage;
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

            public String getBranch_lat() {
                return branch_lat;
            }

            public void setBranch_lat(String branch_lat) {
                this.branch_lat = branch_lat;
            }

            public String getBranch_long() {
                return branch_long;
            }

            public void setBranch_long(String branch_long) {
                this.branch_long = branch_long;
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

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }
        }

        public static class ProtectionBean {
            /**
             * protection_id : 1
             * protection_name : A1
             * protection_status : 1
             */

            private String protection_id;
            private String protection_name;
            private String protection_status;

            public String getProtection_id() {
                return protection_id;
            }

            public void setProtection_id(String protection_id) {
                this.protection_id = protection_id;
            }

            public String getProtection_name() {
                return protection_name;
            }

            public void setProtection_name(String protection_name) {
                this.protection_name = protection_name;
            }

            public String getProtection_status() {
                return protection_status;
            }

            public void setProtection_status(String protection_status) {
                this.protection_status = protection_status;
            }
        }
    }
}
