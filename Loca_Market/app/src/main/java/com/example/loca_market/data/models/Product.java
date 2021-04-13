package com.example.loca_market.data.models;

public class Product {

    private String name;
    private String brand;
    private float price;
    private String categorie ;
    private String description ;
    private String imageUrl ;

    public Product() {
    }

    public Product(String name, String brand, float price, String categorie, String description) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.categorie = categorie;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categori) {
        this.categorie = categori;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getimageUrl() {
        return imageUrl;
    }

    public void setimageUrl(String imgaeUrl) {
        this.imageUrl = imgaeUrl;
    }
}


