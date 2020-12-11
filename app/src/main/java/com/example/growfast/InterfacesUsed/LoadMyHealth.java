package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyHealth {
    void onHealthLoadSuccess(List<ProductEntry> templates);

    void onHealthLoadFailure(String message);
}
