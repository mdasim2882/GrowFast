package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyConcepts {

    void onConceptsLoadSuccess(List<ProductEntry> templates);

    void onConceptsLoadFailure(String message);
}
