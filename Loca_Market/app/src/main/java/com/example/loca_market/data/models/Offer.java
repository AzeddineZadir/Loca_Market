package com.example.loca_market.data.models;

import com.google.firebase.firestore.DocumentId;

import java.util.List;

public class Offer {

    @DocumentId
    private String offerId;
    private String offerTitel;
    private float percentage ;
    private Product offerProduct ;
    private String beginDate ;
    private String endDate ;
    private String sellerId ;

    public Offer() {
    }

    public Offer(String offerId, String offerTitel, float percentage, Product offerProduct, String beginDate, String endDate) {
        this.offerId = offerId;
        this.offerTitel = offerTitel;
        this.percentage = percentage;
        this.offerProduct = offerProduct;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitel() {
        return offerTitel;
    }

    public void setOfferTitel(String offerTitel) {
        this.offerTitel = offerTitel;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public Product getOfferProduct() {
        return offerProduct;
    }

    public void setOfferProduct(Product offerProduct) {
        this.offerProduct = offerProduct;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
