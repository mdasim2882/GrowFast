package com.example.growfast.HelperMethods;

import java.util.List;
import java.util.Map;

public class ProductEntry {
    String productID;

    public String getExpiryDaysLimit() {
        return expiryDaysLimit;
    }

    public void setExpiryDaysLimit(String expiryDaysLimit) {
        this.expiryDaysLimit = expiryDaysLimit;
    }

    String expiryDaysLimit;
    String productImage;
    String productName;

    String productCost;
    List<String> boughtBy;

    private Map<String, Object> purchaseTime;

    public Map<String, Object> getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Map<String, Object> purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public ProductEntry() {
    }

    public List<String> getBoughtBy() {
        return boughtBy;
    }

    public void setBoughtBy(List<String> boughtBy) {
        this.boughtBy = boughtBy;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public ProductEntry(String productImage, String productName, String productCost) {
        this.productImage = productImage;
        this.productName = productName;
        this.productCost = productCost;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    @Override
    public String toString() {
        return "ProductEntry{" +
                "productImage='" + productImage + '\'' +
                ", productName='" + productName + '\'' +
                ", productCost='" + productCost + '\'' +
                '}';
    }
}
