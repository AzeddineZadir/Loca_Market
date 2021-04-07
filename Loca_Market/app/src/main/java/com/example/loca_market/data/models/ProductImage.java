package com.example.loca_market.data.models;

public class ProductImage {
    private String name ;
    private String imageUrl ;

    public ProductImage() {
    }

    public ProductImage(String name, String imageUrl) {

        if (name.trim().equals("")) {
            name = "default_name";
        }
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
