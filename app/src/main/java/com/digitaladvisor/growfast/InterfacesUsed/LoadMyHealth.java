package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyHealth {
    void onHealthLoadSuccess(List<ProductEntry> templates);

    void onHealthLoadFailure(String message);
}
