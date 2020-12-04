package com.example.growfast.InterfacesUsed;


import com.example.growfast.Services.Banners;

import java.util.List;

public interface BannerLoadListener {

    void onBannerLoadSuccess(List<Banners> banners);

    void onBannerLoadFailure(String message);


}