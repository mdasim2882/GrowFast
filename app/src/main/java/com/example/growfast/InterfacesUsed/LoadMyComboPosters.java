package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyComboPosters {
    void onComboPostersLoadSuccess(List<ProductEntry> templates);

    void onComboPostersLoadFailure(String message);
}
