package com.example.alok.test1;

import android.graphics.Bitmap;

/**
 * Created by akp77 on 4/12/2015.
 */
public class Place {
    private int id;
    private String placeName;
    private double longitude;
    private double latitude;
    private Bitmap bitmap;

    public Place() {
    }

    public Place(int id, String placeName, double latitude, double longitude, Bitmap bitmap) {
        this.id = id;
        this.placeName = placeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
