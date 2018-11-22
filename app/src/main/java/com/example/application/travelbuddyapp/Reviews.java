package com.example.application.travelbuddyapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable{

<<<<<<< HEAD
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
=======
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
>>>>>>> 5bf9534eddc3ef617fb6f4d75ee5d8a2d4354e82
        text = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override public Reviews createFromParcel(Parcel in) { return new Reviews(in); }
        @Override public Reviews[] newArray(int size) { return new Reviews[size]; }};

<<<<<<< HEAD
=======
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
>>>>>>> 5bf9534eddc3ef617fb6f4d75ee5d8a2d4354e82
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
<<<<<<< HEAD
        parcel.writeString(review_username);
        parcel.writeString(text);
        parcel.writeString(review_rating);
=======
        parcel.writeString(username);
        parcel.writeString(text);
        parcel.writeString(rating);
>>>>>>> 5bf9534eddc3ef617fb6f4d75ee5d8a2d4354e82
    }
}