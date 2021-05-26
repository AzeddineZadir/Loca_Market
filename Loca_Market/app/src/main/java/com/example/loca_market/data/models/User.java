package com.example.loca_market.data.models;

import java.util.List;

import com.google.firebase.firestore.DocumentId;

public class User {

    @DocumentId
    private String uid ;
    private String username;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private List<Category>preferences;
    private String profilePicURL;

    public User(String username, String email, String role, String firstName, String lastName, String phoneNumber, String address, String city, String postalCode, String country, List<Category> preferences, String profilePicURL) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.preferences = preferences;
        this.profilePicURL = profilePicURL;
    }

    public User(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Category> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Category> preferences) {
        this.preferences = preferences;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

