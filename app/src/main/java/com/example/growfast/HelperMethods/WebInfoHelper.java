package com.example.growfast.HelperMethods;

public class WebInfoHelper {
    String id;
    String name;
    String imageLink;
    String designationName;
    String businessAddress;
    String referenceWebsite;
    String emailId;
    String linkedInPageLink;
    String whatsappNumber;
    String twitterID;
    String telegramD;
    String instagramID;
    String googleMapLocationlink;
    String aboutUs;

    String phoneNo;
    String facebookPageLink;
    String product_0;
    String product_1;
    String product_2;
    String product_3;

    public String getProduct_0() {
        return product_0;
    }

    public void setProduct_0(String product_0) {
        this.product_0 = product_0;
    }

    public String getProduct_1() {
        return product_1;
    }

    public void setProduct_1(String product_1) {
        this.product_1 = product_1;
    }

    public String getProduct_2() {
        return product_2;
    }

    public void setProduct_2(String product_2) {
        this.product_2 = product_2;
    }

    public String getProduct_3() {
        return product_3;
    }

    public void setProduct_3(String product_3) {
        this.product_3 = product_3;
    }

    public String getProduct_4() {
        return product_4;
    }

    public void setProduct_4(String product_4) {
        this.product_4 = product_4;
    }

    String product_4;
//    List<String> productImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public List<String> getProductImages() {
//        return productImages;
//    }
//
//    public void setProductImages(List<String> productImages) {
//        this.productImages = productImages;
//    }


    public WebInfoHelper(String id, String name, String imageLink, String designationName, String businessAddress, String referenceWebsite, String emailId, String linkedInPageLink, String whatsappNumber, String twitterID, String telegramD, String instagramID, String googleMapLocationlink, String aboutUs, String phoneNo, String facebookPageLink, String product_0, String product_1, String product_2, String product_3, String product_4) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
        this.designationName = designationName;
        this.businessAddress = businessAddress;
        this.referenceWebsite = referenceWebsite;
        this.emailId = emailId;
        this.linkedInPageLink = linkedInPageLink;
        this.whatsappNumber = whatsappNumber;
        this.twitterID = twitterID;
        this.telegramD = telegramD;
        this.instagramID = instagramID;
        this.googleMapLocationlink = googleMapLocationlink;
        this.aboutUs = aboutUs;
        this.phoneNo = phoneNo;
        this.facebookPageLink = facebookPageLink;
        this.product_0 = product_0;
        this.product_1 = product_1;
        this.product_2 = product_2;
        this.product_3 = product_3;
        this.product_4 = product_4;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getReferenceWebsite() {
        return referenceWebsite;
    }

    public void setReferenceWebsite(String referenceWebsite) {
        this.referenceWebsite = referenceWebsite;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getLinkedInPageLink() {
        return linkedInPageLink;
    }

    public void setLinkedInPageLink(String linkedInPageLink) {
        this.linkedInPageLink = linkedInPageLink;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public String getTwitterID() {
        return twitterID;
    }

    public void setTwitterID(String twitterID) {
        this.twitterID = twitterID;
    }

    public String getTelegramD() {
        return telegramD;
    }

    public void setTelegramD(String telegramD) {
        this.telegramD = telegramD;
    }

    public String getInstagramID() {
        return instagramID;
    }

    public void setInstagramID(String instagramID) {
        this.instagramID = instagramID;
    }

    public String getGoogleMapLocationlink() {
        return googleMapLocationlink;
    }

    public void setGoogleMapLocationlink(String googleMapLocationlink) {
        this.googleMapLocationlink = googleMapLocationlink;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    @Override
    public String toString() {
        return "WebInfoHelper{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", facebookPageLink='" + facebookPageLink + '\'' +
                ", product_0='" + product_0 + '\'' +
                ", product_1='" + product_1 + '\'' +
                ", product_2='" + product_2 + '\'' +
                ", product_3='" + product_3 + '\'' +
                ", product_4='" + product_4 + '\'' +
                '}';
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
