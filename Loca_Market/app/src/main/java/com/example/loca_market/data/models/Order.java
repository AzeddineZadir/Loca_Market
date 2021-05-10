package com.example.loca_market.data.models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @DocumentId
    private String orderId;
    private String lastName,firstName, phone, address, city, state, date, time, totalAmount,clientId;
    private List<ProductCart> productsOrdred;

    public Order() {
    }


    public Order(String lastName, String firstName, String phone, String address, String city, String state, String date, String time, String totalAmount, String clientId, List<ProductCart> productsBought) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.clientId = clientId;
        this.productsOrdred = productsBought;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<ProductCart> getProductsOrdred() {
        return productsOrdred;
    }

    public void setProductsOrdred(List<ProductCart> productsOrdred) {
        this.productsOrdred = productsOrdred;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
