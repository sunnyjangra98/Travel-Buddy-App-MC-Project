package com.example.application.travelbuddyapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable{

    public Reviews() { }
    public Reviews(String username, String text, String rating) {
        this.review_username = username;
        this.review_rating = rating;
        this.text = text;
    }

    public String getReview_username() { return review_username; }
    public void setReview_username(String review_username) { this.review_username = review_username; }

    String review_username;

    public String getReview_rating() { return review_rating; }
    public void setReview_rating(String review_rating) { this.review_rating = review_rating; }

    String review_rating;
    String text;

    protected Reviews(Parcel in) {
        review_username = in.readString();
        review_rating = in.readString();
        text = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override public Reviews createFromParcel(Parcel in) { return new Reviews(in); }
        @Override public Reviews[] newArray(int size) { return new Reviews[size]; }};

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
        parcel.writeString(review_username);
        parcel.writeString(text);
        parcel.writeString(review_rating);
    }
}