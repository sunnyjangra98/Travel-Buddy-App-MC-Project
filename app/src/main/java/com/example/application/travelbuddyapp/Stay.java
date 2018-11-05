package com.example.application.travelbuddyapp;

import java.io.Serializable;

public class Stay implements Serializable{

    //private int stay_id;
    private Long rating;
    private String brief;
    private String image;
    private String stay_name;
    private String stay_person;

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

    public Long getRating() {
        return rating;
    }
    public String getBrief() {
        return brief;
    }
    public String getImage() {
        return image;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
    public void setRating(Long rating) {
        this.rating = rating;
    }
    public void setImage(String image) {
        this.image = image;
    }
}