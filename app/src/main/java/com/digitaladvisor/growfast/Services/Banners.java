package com.digitaladvisor.growfast.Services;

import androidx.annotation.Keep;

@Keep
public class Banners {
    private String image;

    public Banners() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Banners(String image) {
        this.image = image;
    }
}
