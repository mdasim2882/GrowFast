package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.WhatsappVideoEntry;

import java.util.List;

public interface LoadMyVideos {
    void onVideosImageLoadSuccess(List<WhatsappVideoEntry> templates);

    void onVideosImageLoadFailure(String message);
}
