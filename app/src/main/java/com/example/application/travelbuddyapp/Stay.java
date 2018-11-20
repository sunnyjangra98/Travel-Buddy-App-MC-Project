
package com.example.application.travelbuddyapp;

import android.net.Uri;

import java.io.Serializable;

public class Stay implements Serializable{

    //private int stay_id;
    /*
    private Double rating;
    private String brief;
    private String image;
    private String stay_name;
    private String stay_person;
    private String stay_id;
    */

    public String rating;
    public String brief;
    public Uri image;
    public String stay_name;
    public String stay_person;
    public String stay_id;
    public String details;
    public String interests;
    public String max_people;
    public String things_to_offer;
    public String hostDate;

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }
    public String getMax_people() { return max_people; }
    public void setMax_people(String max_people) { this.max_people = max_people; }
    public String getThings_to_offer() { return things_to_offer; }
    public void setThings_to_offer(String things_to_offer) { this.things_to_offer = things_to_offer; }
    public String getHostDate() {
        return hostDate;
    }
    public void setHostDate(String hostDate) {
        this.hostDate = hostDate;
    }

    /*
        private String hostDate;
        public String getHostDate() {
            return hostDate;
        }
        public void setHostDate(String hostDate) {
            this.hostDate = hostDate;
        }
    */

    public String getStay_id() {
        return stay_id;
    }
    public void setStay_id(String stay_id) {
        this.stay_id = stay_id;
    }
    public String getStay_name() {
        return stay_name;
    }
    public void setStay_name(String stay_name) {
        this.stay_name = stay_name;
    }
    public String getStay_person() {
        return stay_person;
    }
    public void setStay_person(String stay_person) {
        this.stay_person = stay_person;
    }

    public Stay(String stay_id, Uri image, String stay_name, String stay_person, String rating, String hostDate) {
        this.stay_id = stay_id;
        this.image = image;
        this.stay_name = stay_name;
        this.stay_person = stay_person;
        this.rating = rating;
        this.hostDate = hostDate;
    }

    /*
    public Stay(int stay_id, String image, String stay_name, String stay_person, String rating, String brief) {
        this.stay_id = stay_id;
        this.image = image;
        this.stay_name = stay_name;
        this.stay_person = stay_person;
        this.rating = rating;
        this.brief = brief;
    }
    public int getStay_id() {
        return stay_id;
    }
    public void setStay_id(int stay_id) {
        this.stay_id = stay_id;
    }
    */

    public String  getRating() {
        return rating;
    }
    public String getBrief() {
        return brief;
    }
    public Uri getImage() {
        return image;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setImage(Uri image) {
        this.image = image;
    }
}

/*
package com.example.application.travelbuddyapp;
import java.io.Serializable;
public class Stay implements Serializable{
    private String stay_id;
    private int image;
    private String stay_name;
    private String stay_person;
    private String rating;
    private String hostDate;

    public Stay(String stay_id, int image, String stay_name, String stay_person, String rating, String hostDate) {
=======
package com.example.application.travelbuddyapp;

import android.net.Uri;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class Stay implements Serializable{
    private String stay_id;
    public Uri image;
    private String stay_name;
    private String stay_person;
    private float rating;
    private String hostDate;

    public Stay(String stay_id, Uri image, String stay_name, String stay_person, float rating, String hostDate) {
>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
        this.stay_id = stay_id;
        this.image = image;
        this.stay_name = stay_name;
        this.stay_person = stay_person;
        this.rating = rating;
        this.hostDate = hostDate;
    }

    public String getStay_id() {
        return stay_id;
    }

<<<<<<< HEAD
    public int getImage() {
=======
    public Uri getImage() {
>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
        return image;
    }

    public String getStay_name() {
        return stay_name;
    }

    public String getStay_person() {
        return stay_person;
    }

<<<<<<< HEAD
    public String getRating() {
=======
    public float getRating() {
>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
        return rating;
    }

    public String gethostDate() {
        return hostDate;
    }

    public void setStay_id(String stay_id) {
        this.stay_id = stay_id;
    }

<<<<<<< HEAD
    public void setImage(int image) {
=======
    public void setImage(Uri image) {
>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
        this.image = image;
    }

    public void setStay_name(String stay_name) {
        this.stay_name = stay_name;
    }

    public void setStay_person(String stay_person) {
        this.stay_person = stay_person;
    }

<<<<<<< HEAD
    public void setRating(String rating) {
=======
    public void setRating(float rating) {
>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
        this.rating = rating;
    }

    public void sethostDate(String hostDate) {
        this.hostDate = hostDate;
    }
}
<<<<<<< HEAD
*/
