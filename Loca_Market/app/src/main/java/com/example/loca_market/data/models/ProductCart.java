package com.example.loca_market.data.models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class ProductCart implements Serializable {
    @DocumentId
    private String docId ;
    Product product;
    int quantity;

    public ProductCart() {
    }

    public ProductCart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
