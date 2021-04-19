package com.example.loca_market.data.models;

public class Product {

    private String name;
    private String brand;
    private float price;
    private String category;
    private String description ;
    private String imageUrl ;
    private String productOwner ;
    private boolean bestSell;


    public Product() {
    }

    public Product(String name, String brand, float price, String category, String description, String imageUrl, String productOwner) {
    public Product(String name, String brand, float price, String category, String description, String imageUrl, boolean bestSell) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.productOwner = productOwner;
        this.bestSell = bestSell;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }
}

    public boolean isBestSell() {
        return bestSell;
    }

    public void setBestSell(boolean bestSell) {
        this.bestSell = bestSell;
    }
}

