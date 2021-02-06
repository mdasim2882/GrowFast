package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;

import java.util.List;

public interface LoadMyTemplates {
    void onTemplateLoadSuccess(List<ProductEntry> templates);

    void onTemplatedLoadFailure(String message);
}
