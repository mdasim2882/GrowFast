package com.example.growfast.HelperMethods;

import java.util.List;

public class WebInfoHelper {
    String id;
    String name;
    String imageLink;
    String phoneNo;
    String facebookPageLink;
    List<String> productImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    public WebInfoHelper(String id, String name, String imageLink, String phoneNo, String facebookPageLink, List<String> productImages) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
        this.phoneNo = phoneNo;
        this.facebookPageLink = facebookPageLink;
        this.productImages = productImages;
    }

    public WebInfoHelper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFacebookPageLink() {
        return facebookPageLink;
    }

    public void setFacebookPageLink(String facebookPageLink) {
        this.facebookPageLink = facebookPageLink;
    }
}
