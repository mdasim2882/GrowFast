package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyTemplates {
    void onTemplateLoadSuccess(List<ProductEntry> templates);

    void onTemplatedLoadFailure(String message);
}
