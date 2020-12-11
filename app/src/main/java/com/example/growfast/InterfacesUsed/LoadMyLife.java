package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyLife {
    void onLifeLoadSuccess(List<ProductEntry> templates);

    void onLifeLoadFailure(String message);
}
