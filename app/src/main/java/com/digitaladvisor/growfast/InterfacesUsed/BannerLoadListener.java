package com.digitaladvisor.growfast.InterfacesUsed;


import com.digitaladvisor.growfast.Services.Banners;

import java.util.List;

public interface BannerLoadListener {

    void onBannerLoadSuccess(List<Banners> banners);

    void onBannerLoadFailure(String message);


}