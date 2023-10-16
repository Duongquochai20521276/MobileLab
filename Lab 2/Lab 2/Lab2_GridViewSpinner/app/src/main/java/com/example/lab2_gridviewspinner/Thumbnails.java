package com.example.lab2_gridviewspinner;

public enum Thumbnails {
    Thumbnails1("Thumbnail 1", R.drawable.dish_1),
    Thumbnails2("Thumbnail 2", R.drawable.dish_2),
    Thumbnails3("Thumbnail 3", R.drawable.dish_3),
    Thumbnails4("Thumbnail 4", R.drawable.dish_4);

    private String name;
    private int img;

    Thumbnails(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}