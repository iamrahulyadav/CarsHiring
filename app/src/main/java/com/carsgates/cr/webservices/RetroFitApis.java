package com.carsgates.cr.webservices;


import com.carsgates.cr.models.BookingData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Atul kUmar Gupta on 04-11-2016.
 */

public interface RetroFitApis {
    @FormUrlEncoded
    @POST("token")
    Call<ApiResponse> token(@Field("grant_type") String grant_type,
                            @Field("client_id") String id,
                            @Field("client_secret") String secret);

    @FormUrlEncoded
    @POST("webservice/login")
    Call<ApiResponse> login(@Field("access_token") String access_token,
                            @Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("webservice/signup")
    Call<ApiResponse> signup(@Field("access_token") String access_token,
                             @Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("webservice/lang_list")
    Call<ApiResponse> lang_list(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("webservice/forgot_pass")
    Call<ApiResponse> forgot_pass(@Field("access_token") String access_token,
                                  @Field("email") String email);

    @FormUrlEncoded
    @POST("webservice/change_pass")
    Call<ApiResponse> change_pass(@Field("access_token") String access_token,
                                  @Field("oldpass") String oldpass,
                                  @Field("newpass") String newpass,
                                  @Field("userid") String userid);

    @FormUrlEncoded
    @POST("webservice/contact_us")
    Call<ApiResponse> contact_us(@Field("access_token") String access_token,
                                 @Field("language") String language);

    @FormUrlEncoded
    @POST("webservice/about_us")
    Call<ApiResponse> about_us(@Field("access_token") String access_token,
                               @Field("language") String language);
    @FormUrlEncoded
    @POST("webservice/car_detail")
    Call<ApiResponse> car_detail(@Field("access_token") String access_token,
                                 @Field("car_id") String id);
    @FormUrlEncoded
    @POST("webservice/update_profile")
    Call<ApiResponse> update_profile(@Field("access_token") String access_token,
                                     @Field("title") String title,
                                     @Field("first_name") String fname,
                                     @Field("last_name") String lname,
                                     @Field("phone") String phone,
                                     @Field("age") int age,
                                     @Field("user_id") String user_id );
    @FormUrlEncoded
    @POST("webservice/add_driver")
    Call<ApiResponse> add_driver(@Field("access_token") String access_token,
                                 @Field("title") String title,
                                 @Field("age") int age,
                                 @Field("fname") String fname,
                                 @Field("lname") String lname,
                                 @Field("email") String email,
                                 @Field("phone") String phone,
                                 @Field("user_id") String user_id,
                                 @Field("passport_no") String passport_no,
                                 @Field("driving_license") String driving_license,
                                 @Field("save_next") String save_next,
                                 @Field("newsletter") String newsletter,
                                 @Field("license_origin") String license_origin);

    @FormUrlEncoded
    @POST("webservice/driver_list")
    Call<ApiResponse> driver_list(@Field("access_token") String access_token,
                                  @Field("user_id") String id);

    @FormUrlEncoded
    @POST("webservice/card_list")
    Call<ApiResponse> card_list(@Field("access_token") String access_token,
                                @Field("user_id") String id);

    @FormUrlEncoded
    @POST("webservice/driver_detail")
    Call<ApiResponse> driver_detail(@Field("access_token") String access_token,
                                    @Field("user_id") String id,
                                    @Field("driver_id") String driver_id);
    @FormUrlEncoded
    @POST("webservice/update_driver")
    Call<ApiResponse> update_driver(@Field("access_token") String access_token,
                                    @Field("title") String title,
                                    @Field("age") int age,
                                    @Field("fname") String fname,
                                    @Field("lname") String lname,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("user_id") String user_id,
                                    @Field("passport_no") String passport_no,
                                    @Field("driving_license") String driving_license,
                                    @Field("save_next") String save_next,
                                    @Field("newsletter") String newsletter,
                                    @Field("license_origin") String license_origin,
                                    @Field("driver_id") String driver_id);
/*
    @FormUrlEncoded
    @POST("webservice/search")
    Call<ApiResponse> search(@Field("access_token") String access_token,
                             @Field("pick_city") String pick_city,
                             @Field("pick_date") String pick_date,
                             @Field("pick_houre") String pick_hour,
                             @Field("pick_minute") String pick_minute,
                             @Field("drop_city") String drop_city,
                             @Field("drop_date") String drop_date,
                             @Field("drop_houre") String drop_hour,
                             @Field("drop_minute") String drop_minute,
                             @Field("driver_age") String driver_age,
                             @Field("use_current_location") int useCurrentLocation,
                             @Field("sameas_pick_location") int useSameDropLocation,
                             @Field("between_driver_age") int betweenDriverAge,
                             @Field("lat") double lat,
                             @Field("long") double lng,
                             @Field("location_code") String location_code,
                             @Field("location_iata") String location_iata,
                             @Field("location_type") String location_type,
                             @Field("location_code_drop") String location_code_drop,
                             @Field("location_iata_drop") String location_iata_drop,
                             @Field("location_type_drop") String location_type_drop,
                             @Field("language_code") String language_code);
*/

    @FormUrlEncoded
    @POST("webservice/search")
    Call<ApiResponse> search(@Field("access_token") String access_token,
                             @Field("pick_city") String pick_city,
                             @Field("pick_date") String pick_date,
                             @Field("pick_houre") String pick_hour,
                             @Field("pick_minute") String pick_minute,
                             @Field("drop_city") String drop_city,
                             @Field("drop_date") String drop_date,
                             @Field("drop_houre") String drop_hour,
                             @Field("drop_minute") String drop_minute,
                             @Field("driver_age") String driver_age,
                             @Field("use_current_location") int useCurrentLocation,
                             @Field("sameas_pick_location") int useSameDropLocation,
                             @Field("between_driver_age") int betweenDriverAge,
                             @Field("lat") double lat,
                             @Field("long") double lng,
                             @Field("location_code") String location_code,
                             @Field("location_iata") String location_iata,
                             @Field("location_type") String location_type,
                             @Field("location_code_drop") String location_code_drop,
                             @Field("location_iata_drop") String location_iata_drop,
                             @Field("location_type_drop") String location_type_drop,
                             @Field("language_code") String language_code);

    @FormUrlEncoded
    @POST("webservice/add_card")
    Call<ApiResponse> add_card(@Field("access_token") String access_token,
                               @Field("card_name") String card_name,
                               @Field("card_number") String card_number,
                               @Field("ex_date") String ex_date,
                               @Field("cvv") String cvv,
                               @Field("country") String country, @Field("zipcode") String zipcode,
                               @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("webservice/location_list")
    Call<ApiResponse> location_list(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("webservice/booking_detail")
    Call<ApiResponse> getBooking(@Field("access_token") String access_token,
                                 @Field("user_id") String id);

}
