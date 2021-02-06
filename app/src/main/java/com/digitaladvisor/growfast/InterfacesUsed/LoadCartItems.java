package com.digitaladvisor.growfast.InterfacesUsed;

import com.digitaladvisor.growfast.HelperMethods.CartHelp;

import java.util.List;

public interface LoadCartItems {
    void onCartItemsLoadSuccess(List<CartHelp> templates);

    void onCartItemsLoadFailure(String message);
}
