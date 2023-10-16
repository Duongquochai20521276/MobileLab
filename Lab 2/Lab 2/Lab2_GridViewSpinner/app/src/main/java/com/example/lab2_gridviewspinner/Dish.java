package com.example.lab2_gridviewspinner;

import java.io.Serializable;

public class Dish implements Serializable {
    private String name;
    private boolean promotion;
    private Thumbnails thumbnails;

    public Dish() {}

    public Thumbnails getThumbnail() {
        return thumbnails;
    }

    public void setThumbnail(Thumbnails thumbnail) {
        this.thumbnails = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSale() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }
}
