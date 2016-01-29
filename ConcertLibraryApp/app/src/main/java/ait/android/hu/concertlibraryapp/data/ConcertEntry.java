package ait.android.hu.concertlibraryapp.data;

import android.graphics.Bitmap;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

/**
 * Created by emily on 11/22/15.
 */

@ParseClassName("ConcertEntry")
public class ConcertEntry extends ParseObject implements Serializable  {

    private String bandName;
    private String location;
    private String date;
    private String video;
    private Bitmap thumb;
    private String nameUser;
    private double rating;

    private boolean videoInserted;

    public ConcertEntry() {
    }

    public ConcertEntry(String bandName, String video, boolean videoInserted, String location, String date, Bitmap thumb, String nameUser, double rating) {
        this.bandName = bandName;
        this.video = video;
        this.videoInserted = videoInserted;
        this.location = location;
        this.thumb = thumb;
        this.date = date;
        this.nameUser = nameUser;
        this.rating = rating;
    }

    public double getCurrentRating(){
        return getDouble("rating");
    }

    public String getBandName() {
        return getString("bandName");
    }

    public String getNameUser(){
        return getString("nameUser");
    }

    public String getLocation() {
        return getString("location");
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public String getDate() {
        return getString("date");
    }

    public String getVideo() {
        return getString("video");
    }

    public boolean isVideoInserted() {
        return getBoolean("videoInserted");
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
        put("bandName", bandName);
    }

    public void setRating(double rating){
        this.rating = rating;
        put("rating", rating);
    }

    public  void setNameUser(String nameUser){
        this.nameUser = nameUser;
        put("nameUser", nameUser);
    }


    public void setVideo(String video) {
        this.video = video;
        if (video != null){
            put("video", video);
        }
    }

    public void setLocation(String location) {
        this.location = location;
        put("location", location);
    }

    public void setDate(String date) {
        this.date = date;
        put("date", date);
    }

    public void setThumb(Bitmap image) {
        this.thumb = thumb;
    }

    public void setVideoInserted(boolean videoInserted) {
        this.videoInserted = videoInserted;
        put("videoInserted", videoInserted);
    }


}
