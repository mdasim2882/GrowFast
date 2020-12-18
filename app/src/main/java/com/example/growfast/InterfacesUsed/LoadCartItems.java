package com.example.growfast.InterfacesUsed;

import com.example.growfast.HelperMethods.CartHelp;

import java.util.List;

public interface LoadCartItems {
    void onCartItemsLoadSuccess(List<CartHelp> templates);

    void onCartItemsLoadFailure(String message);
}
