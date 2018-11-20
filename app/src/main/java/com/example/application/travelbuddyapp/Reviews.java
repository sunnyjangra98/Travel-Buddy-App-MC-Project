package com.example.application.travelbuddyapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable{

    public Reviews(String username, String text, String rating) {
        this.username = username;
        this.rating = rating;
        this.text = text;
    }

    String username;
    String rating;
    String text;

    protected Reviews(Parcel in) {
        username = in.readString();
        /*if (in.readByte() == 0) {
            rating = Double.valueOf(0);
        } else {
            rating = in.readDouble();
        }*/
        rating = in.readString();
        text = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override public Reviews createFromParcel(Parcel in) { return new Reviews(in); }
        @Override public Reviews[] newArray(int size) { return new Reviews[size]; }};

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) { this.rating = rating; }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(text);
        parcel.writeString(rating);
    }
}