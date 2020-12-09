package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.WhatsappVideoEntry;

import java.util.List;

public interface LoadMyVideos {
    void onVideosImageLoadSuccess(List<WhatsappVideoEntry> templates);

    void onVideosImageLoadFailure(String message);
}
