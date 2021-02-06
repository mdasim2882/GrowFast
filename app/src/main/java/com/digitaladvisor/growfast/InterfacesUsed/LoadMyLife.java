package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyLife {
    void onLifeLoadSuccess(List<ProductEntry> templates);

    void onLifeLoadFailure(String message);
}
