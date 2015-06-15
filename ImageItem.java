package com.example.alok.test1;

import android.graphics.Bitmap;

public class ImageItem {
    private int id;
    private Bitmap image;
    private String title;


    public ImageItem(int id, Bitmap image, String title) {
        super();
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
