package com.example.loca_market.data.models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class ProductCart implements Serializable {
    @DocumentId
    private String docId ;
    Product product;
    int quantity;
    boolean status ;
    String orderId;
    public ProductCart() {

    }

    public ProductCart(Product product, int quantity,String orderId) {
        this.product = product;
        this.quantity = quantity;
        this.status =false;
        this.orderId=orderId;

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
