package com.example.application.travelbuddyapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Requests implements Parcelable
{
    String NumberOfTraveller;
    String dateToStay;
    String status;
    String user_id;
    String username;
    String stay_city;
    String requested_stay_id;

    public String getStay_city() { return stay_city; }
    public void setStay_city(String stay_city) { this.stay_city = stay_city; }
    public String getRequested_stay_id() {
        return requested_stay_id;
    }
    public void setRequested_stay_id(String requested_stay_id) { this.requested_stay_id = requested_stay_id; }

    public Requests() { }

    protected Requests(Parcel in) {
        NumberOfTraveller = in.readString();
        dateToStay = in.readString();
        status = in.readString();
        user_id = in.readString();
        username = in.readString();
    }

    public static final Creator<Requests> CREATOR = new Creator<Requests>() {
        @Override public Requests createFromParcel(Parcel in) { return new Requests(in); }
        @Override public Requests[] newArray(int size) { return new Requests[size]; }};

    public String getNumberOfTraveller() {
        return NumberOfTraveller;
    }
    public void setNumberOfTraveller(String numberOfTraveller) { NumberOfTraveller = numberOfTraveller; }
    public String getDateToStay() {
        return dateToStay;
    }
    public void setDateToStay(String dateToStay) {
        this.dateToStay = dateToStay;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(NumberOfTraveller);
        parcel.writeString(dateToStay);
        parcel.writeString(status);
        parcel.writeString(user_id);
        parcel.writeString(username);
    }
}
