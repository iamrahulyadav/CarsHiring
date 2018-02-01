package com.carsgates.cr.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rakhi on 2/1/2018.
 */

public class SearchData implements Parcelable{
            private String status;
            private FeatureBean feature;
            private String covprice;
            private String covcurrency;
            private String category;
            private String model;
            private String model_code;
            private String image;
            @SerializedName("package")
            private String packageX;
            private String price;
            private String currency;
            private String time_unit;
            private String time;
            private String id_context;
            private String refer_type;
            private String deposit_currency;
            private String deposit_price;
            private String deposit_desc;
            private String deposit_name;
            private String supplier;
            private String supplier_city;
            private String supplier_logo;
            private String drop_city;
            private String tc;
            private String rule;
            private String type;
            private List<CoveragesBean> coverages;

    protected SearchData(Parcel in) {
        status = in.readString();
        feature = in.readParcelable(FeatureBean.class.getClassLoader());
        covprice = in.readString();
        covcurrency = in.readString();
        category = in.readString();
        model = in.readString();
        model_code = in.readString();
        image = in.readString();
        packageX = in.readString();
        price = in.readString();
        currency = in.readString();
        time_unit = in.readString();
        time = in.readString();
        id_context = in.readString();
        refer_type = in.readString();
        deposit_currency = in.readString();
        deposit_price = in.readString();
        deposit_desc = in.readString();
        deposit_name = in.readString();
        supplier = in.readString();
        supplier_city = in.readString();
        supplier_logo = in.readString();
        drop_city = in.readString();
        tc = in.readString();
        rule = in.readString();
        type = in.readString();
        coverages = in.createTypedArrayList(CoveragesBean.CREATOR);
    }

    public static final Creator<SearchData> CREATOR = new Creator<SearchData>() {
        @Override
        public SearchData createFromParcel(Parcel in) {
            return new SearchData(in);
        }

        @Override
        public SearchData[] newArray(int size) {
            return new SearchData[size];
        }
    };

    public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public FeatureBean getFeature() {
                return feature;
            }

            public void setFeature(FeatureBean feature) {
                this.feature = feature;
            }

            public String getCovprice() {
                return covprice;
            }

            public void setCovprice(String covprice) {
                this.covprice = covprice;
            }

            public String getCovcurrency() {
                return covcurrency;
            }

            public void setCovcurrency(String covcurrency) {
                this.covcurrency = covcurrency;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getModel_code() {
                return model_code;
            }

            public void setModel_code(String model_code) {
                this.model_code = model_code;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getTime_unit() {
                return time_unit;
            }

            public void setTime_unit(String time_unit) {
                this.time_unit = time_unit;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getId_context() {
                return id_context;
            }

            public void setId_context(String id_context) {
                this.id_context = id_context;
            }

            public String getRefer_type() {
                return refer_type;
            }

            public void setRefer_type(String refer_type) {
                this.refer_type = refer_type;
            }

            public String getDeposit_currency() {
                return deposit_currency;
            }

            public void setDeposit_currency(String deposit_currency) {
                this.deposit_currency = deposit_currency;
            }

            public String getDeposit_price() {
                return deposit_price;
            }

            public void setDeposit_price(String deposit_price) {
                this.deposit_price = deposit_price;
            }

            public String getDeposit_desc() {
                return deposit_desc;
            }

            public void setDeposit_desc(String deposit_desc) {
                this.deposit_desc = deposit_desc;
            }

            public String getDeposit_name() {
                return deposit_name;
            }

            public void setDeposit_name(String deposit_name) {
                this.deposit_name = deposit_name;
            }

            public String getSupplier() {
                return supplier;
            }

            public void setSupplier(String supplier) {
                this.supplier = supplier;
            }

            public String getSupplier_city() {
                return supplier_city;
            }

            public void setSupplier_city(String supplier_city) {
                this.supplier_city = supplier_city;
            }

            public String getSupplier_logo() {
                return supplier_logo;
            }

            public void setSupplier_logo(String supplier_logo) {
                this.supplier_logo = supplier_logo;
            }

            public String getDrop_city() {
                return drop_city;
            }

            public void setDrop_city(String drop_city) {
                this.drop_city = drop_city;
            }

            public String getTc() {
                return tc;
            }

            public void setTc(String tc) {
                this.tc = tc;
            }

            public String getRule() {
                return rule;
            }

            public void setRule(String rule) {
                this.rule = rule;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<CoveragesBean> getCoverages() {
                return coverages;
            }

            public void setCoverages(List<CoveragesBean> coverages) {
                this.coverages = coverages;
            }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeParcelable(feature, i);
        parcel.writeString(covprice);
        parcel.writeString(covcurrency);
        parcel.writeString(category);
        parcel.writeString(model);
        parcel.writeString(model_code);
        parcel.writeString(image);
        parcel.writeString(packageX);
        parcel.writeString(price);
        parcel.writeString(currency);
        parcel.writeString(time_unit);
        parcel.writeString(time);
        parcel.writeString(id_context);
        parcel.writeString(refer_type);
        parcel.writeString(deposit_currency);
        parcel.writeString(deposit_price);
        parcel.writeString(deposit_desc);
        parcel.writeString(deposit_name);
        parcel.writeString(supplier);
        parcel.writeString(supplier_city);
        parcel.writeString(supplier_logo);
        parcel.writeString(drop_city);
        parcel.writeString(tc);
        parcel.writeString(rule);
        parcel.writeString(type);
        parcel.writeTypedList(coverages);
    }

    public static class FeatureBean implements Parcelable{
                /**
                 * aircondition : true
                 * transmission : Manual
                 * fueltype : Petrol
                 * bag : 0
                 * door : 2
                 */

                private String aircondition;
                private String transmission;
                private String fueltype;
                private String bag;
                private String door;

        protected FeatureBean(Parcel in) {
            aircondition = in.readString();
            transmission = in.readString();
            fueltype = in.readString();
            bag = in.readString();
            door = in.readString();
        }

        public static final Creator<FeatureBean> CREATOR = new Creator<FeatureBean>() {
            @Override
            public FeatureBean createFromParcel(Parcel in) {
                return new FeatureBean(in);
            }

            @Override
            public FeatureBean[] newArray(int size) {
                return new FeatureBean[size];
            }
        };

        public String getAircondition() {
                    return aircondition;
                }

                public void setAircondition(String aircondition) {
                    this.aircondition = aircondition;
                }

                public String getTransmission() {
                    return transmission;
                }

                public void setTransmission(String transmission) {
                    this.transmission = transmission;
                }

                public String getFueltype() {
                    return fueltype;
                }

                public void setFueltype(String fueltype) {
                    this.fueltype = fueltype;
                }

                public String getBag() {
                    return bag;
                }

                public void setBag(String bag) {
                    this.bag = bag;
                }

                public String getDoor() {
                    return door;
                }

                public void setDoor(String door) {
                    this.door = door;
                }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(aircondition);
            parcel.writeString(transmission);
            parcel.writeString(fueltype);
            parcel.writeString(bag);
            parcel.writeString(door);
        }
    }

            public static class CoveragesBean implements Parcelable{
                /**
                 * code : 416
                 * name : Kilometer inclusive: 600 km (0,37 EUR/km)
                 * currency : null
                 * amount : null
                 * desc : null
                 * currency2 : EUR
                 * amount2 : 0.37
                 */

                private String code;
                private String name;
                private Object currency;
                private Object amount;
                private Object desc;
                private String currency2;
                private String amount2;

                protected CoveragesBean(Parcel in) {
                    code = in.readString();
                    name = in.readString();
                    currency2 = in.readString();
                    amount2 = in.readString();
                }

                public static final Creator<CoveragesBean> CREATOR = new Creator<CoveragesBean>() {
                    @Override
                    public CoveragesBean createFromParcel(Parcel in) {
                        return new CoveragesBean(in);
                    }

                    @Override
                    public CoveragesBean[] newArray(int size) {
                        return new CoveragesBean[size];
                    }
                };

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getCurrency() {
                    return currency;
                }

                public void setCurrency(Object currency) {
                    this.currency = currency;
                }

                public Object getAmount() {
                    return amount;
                }

                public void setAmount(Object amount) {
                    this.amount = amount;
                }

                public Object getDesc() {
                    return desc;
                }

                public void setDesc(Object desc) {
                    this.desc = desc;
                }

                public String getCurrency2() {
                    return currency2;
                }

                public void setCurrency2(String currency2) {
                    this.currency2 = currency2;
                }

                public String getAmount2() {
                    return amount2;
                }

                public void setAmount2(String amount2) {
                    this.amount2 = amount2;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeString(code);
                    parcel.writeString(name);
                    parcel.writeString(currency2);
                    parcel.writeString(amount2);
                }
            }


}
