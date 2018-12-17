package com.example.application.travelbuddyapp;

import java.io.Serializable;

public class travel implements Serializable {
    String travel_id;
    String image;
    String place;
    String details;
    String host;
    String no_of_going;
    String interested;

    public travel(){

    }

    public travel(String travel_id, String image, String place, String host, String no_of_going, String details, String interested) {
        this.travel_id = travel_id;
        this.image = image;
        this.place = place;
        this.host = host;
        this.no_of_going = no_of_going;
        this.details = details;
        this.interested = interested;
    }

    public String gettravel_id() {
        return travel_id;
    }

    public String getInterested() {
        return interested;
    }

    public String getImage() {
        return image;
    }

    public String getplace() {
        return place;
    }

    public String gethost() {
        return host;
    }

    public String getno_of_going() {
        return no_of_going;
    }

    public String getdetails() {
        return details;
    }

    public void settravel_id(String travel_id) {
        this.travel_id = travel_id;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setplace(String place) {
        this.place = place;
    }

    public void sethost(String host) {
        this.host = host;
    }

    public void setno_of_going(String no_of_going) {
        this.no_of_going = no_of_going;
    }

    public void setdetails(String details) {
        this.details = details;
    }
}
