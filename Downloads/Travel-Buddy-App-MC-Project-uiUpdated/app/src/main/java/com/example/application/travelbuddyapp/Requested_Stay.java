package com.example.application.travelbuddyapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Requested_Stay implements Parcelable
{
    String requested_stay_name;
    String requested_stay_person;
    String requested_city;
    String requested_status;

    public String getRequested_stay_name() { return requested_stay_name; }
    public void setRequested_stay_name(String requested_stay_name) { this.requested_stay_name = requested_stay_name; }
    public String getRequested_stay_person() { return requested_stay_person; }
    public void setRequested_stay_person(String requested_stay_person) { this.requested_stay_person = requested_stay_person; }
    public String getRequested_city() { return requested_city; }
    public void setRequested_city(String requested_city) { this.requested_city = requested_city; }

    public String getRequested_status() { return requested_status; }
    public void setRequested_status(String requested_status) { this.requested_status = requested_status; }

    public Requested_Stay(){}

    protected Requested_Stay(Parcel in) {
        requested_stay_name = in.readString();
        requested_city = in.readString();
        requested_stay_person = in.readString();
        requested_status = in.readString();
    }

    public static final Creator<Requested_Stay> CREATOR = new Creator<Requested_Stay>() {
        @Override public Requested_Stay createFromParcel(Parcel in) { return new Requested_Stay(in); }
        @Override public Requested_Stay[] newArray(int size) { return new Requested_Stay[size]; }};

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(requested_stay_name);
        parcel.writeString(requested_city);
        parcel.writeString(requested_stay_person);
        parcel.writeString(requested_status);
    }
}
