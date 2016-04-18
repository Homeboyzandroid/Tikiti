package com.tikiti.event.Utils;

import android.graphics.Bitmap;

/**
 * Created by homeboyz on 3/24/16.
 */
public class Event {
    private String title, image;
    private String date;
    public int id;
    private String description;
    private String location;
    private String catName;
    private String mpesaacc;

    public Event(int id, String name, String description, String image, String location , String date, String catName, String mpesaacc) {
        this.id = id;
        this.title = name;
        this.image = image;
        this.date = date;
        this.description = description;
        this.location = location;
        this.mpesaacc = mpesaacc;
        this.catName = catName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public String getCatName(){
        return catName;
    }
    public void setCatName(String catName){
        this.catName = catName;
    }

    public String getMpesaacc(){
        return mpesaacc;
    }
    public void setMpesaacc(String mpesaacc){
        this.mpesaacc = mpesaacc;
    }
}
