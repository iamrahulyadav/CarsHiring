package com.carsgates.cr.models;

/**
 * Created by sony on 09-05-2017.
 */

public class BookingModel {
    String BookingNumber,date,rate;
    public BookingModel()
    {

    }

    public BookingModel(String BookingNumber, String date, String rate) {
        this.BookingNumber=BookingNumber;
        this.date=date;
        this.rate=rate;
    }

    public String getBookingNumber() {
        return BookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        BookingNumber = bookingNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


}
