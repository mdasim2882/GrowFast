package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyConcepts {

    void onConceptsLoadSuccess(List<ProductEntry> templates);

    void onConceptsLoadFailure(String message);
}
