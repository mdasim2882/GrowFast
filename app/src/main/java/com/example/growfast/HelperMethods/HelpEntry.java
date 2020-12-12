package com.example.growfast.HelperMethods;

public class HelpEntry {
    String videoHelpLink;
    String phoneNo1;
    String phoneNo2;
    String refundPolicyLink;
    String PrivacyPolicyLink;
    String descriptionHelp;
    String supportEmail;
    String paymentLink;
    String referAndEarnLink;


    public String getDescriptionHelp() {
        return descriptionHelp;
    }

    public void setDescriptionHelp(String descriptionHelp) {
        this.descriptionHelp = descriptionHelp;
    }

    public HelpEntry(String videoHelpLink, String phoneNo1, String phoneNo2, String refundPolicyLink, String privacyPolicyLink, String descriptionHelp, String supportEmail, String paymentLink, String referAndEarnLink) {
        this.videoHelpLink = videoHelpLink;
        this.phoneNo1 = phoneNo1;
        this.phoneNo2 = phoneNo2;
        this.refundPolicyLink = refundPolicyLink;
        PrivacyPolicyLink = privacyPolicyLink;
        this.descriptionHelp = descriptionHelp;
        this.supportEmail = supportEmail;
        this.paymentLink = paymentLink;
        this.referAndEarnLink = referAndEarnLink;
    }


    public HelpEntry() {
    }


    public String getVideoHelpLink() {
        return videoHelpLink;
    }

    public void setVideoHelpLink(String videoHelpLink) {
        this.videoHelpLink = videoHelpLink;
    }

    public String getPhoneNo1() {
        return phoneNo1;
    }

    public void setPhoneNo1(String phoneNo1) {
        this.phoneNo1 = phoneNo1;
    }

    public String getPhoneNo2() {
        return phoneNo2;
    }

    public void setPhoneNo2(String phoneNo2) {
        this.phoneNo2 = phoneNo2;
    }

    public String getRefundPolicyLink() {
        return refundPolicyLink;
    }

    public void setRefundPolicyLink(String refundPolicyLink) {
        this.refundPolicyLink = refundPolicyLink;
    }

    public String getPrivacyPolicyLink() {
        return PrivacyPolicyLink;
    }

    public void setPrivacyPolicyLink(String privacyPolicyLink) {
        PrivacyPolicyLink = privacyPolicyLink;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getReferAndEarnLink() {
        return referAndEarnLink;
    }

    public void setReferAndEarnLink(String referAndEarnLink) {
        this.referAndEarnLink = referAndEarnLink;
    }
}
