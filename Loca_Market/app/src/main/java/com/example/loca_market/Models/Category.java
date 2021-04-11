package com.example.loca_market.Models;

public class Category {
    String type;
    String img_url;

    public Category() {
    }

    public Category(String type, String img_url) {
        this.type = type;
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
