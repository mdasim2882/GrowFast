package com.example.growfast.HelperMethods;

import java.util.List;

public class WhatsappVideoEntry {
    String productID;
    String productImage;
    String videoProductLink;
    String productName;
    String productCost;
    List<String> boughtBy;

    public List<String> getBoughtBy() {
        return boughtBy;
    }

    public void setBoughtBy(List<String> boughtBy) {
        this.boughtBy = boughtBy;
    }

    public WhatsappVideoEntry() {
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public WhatsappVideoEntry(String productImage, String videoProductLink, String productName, String productCost) {
        this.productImage = productImage;
        this.videoProductLink = videoProductLink;
        this.productName = productName;
        this.productCost = productCost;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getVideoProductLink() {
        return videoProductLink;
    }

    public void setVideoProductLink(String videoProductLink) {
        this.videoProductLink = videoProductLink;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCost() {
        return productCost;
    }

    public void setProductCost(String productCost) {
        this.productCost = productCost;
    }
}
