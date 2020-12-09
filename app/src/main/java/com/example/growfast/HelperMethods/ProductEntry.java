package com.example.growfast.HelperMethods;

public class ProductEntry {
    String productImage;
    String productName;
    String productCost;

    public ProductEntry() {
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
