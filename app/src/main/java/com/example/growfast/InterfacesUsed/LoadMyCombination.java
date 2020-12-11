package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyCombination {
    void onCombinationsLoadSuccess(List<ProductEntry> templates);

    void onCombinationsLoadFailure(String message);
}
