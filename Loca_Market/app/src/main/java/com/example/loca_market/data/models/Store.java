package com.example.loca_market.data.models;

import com.google.firebase.firestore.DocumentId;

public class Store {
    @DocumentId
    private String sid ;
    private String name;
    private String category;
    private int color ;
    private String description ;
    private String imageUrl ;
    private boolean grildView ;
    private String sellerName;
    public Store() {
    }

    public Store(String sid, String name, String category, int color, String description, String imageUrl, boolean grildView, String sellerName) {
        this.sid = sid;
        this.name = name;
        this.category = category;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
        this.grildView = grildView;
        this.sellerName = sellerName;
    }

    public Store(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isGrildView() {
        return grildView;
    }

    public void setGrildView(boolean grildView) {
        this.grildView = grildView;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
