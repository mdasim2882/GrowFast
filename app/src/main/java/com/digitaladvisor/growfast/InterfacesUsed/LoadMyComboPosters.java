package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyComboPosters {
    void onComboPostersLoadSuccess(List<ProductEntry> templates);

    void onComboPostersLoadFailure(String message);
}
