package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyCombination {
    void onCombinationsLoadSuccess(List<ProductEntry> templates);

    void onCombinationsLoadFailure(String message);
}
