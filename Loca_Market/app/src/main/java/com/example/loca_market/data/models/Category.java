package com.example.loca_market.data.models;

public class Category {
   String name ;
   String descrioption ;
   String imageUrl ;

    public Category() {
    }

    public Category(String name, String descrioption, String imageUrl) {
        this.name = name;
        this.descrioption = descrioption;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrioption() {
        return descrioption;
    }

    public void setDescrioption(String descrioption) {
        this.descrioption = descrioption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
