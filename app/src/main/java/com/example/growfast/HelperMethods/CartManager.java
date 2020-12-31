package com.example.growfast.HelperMethods;

import java.util.LinkedList;
import java.util.List;

public class CartManager {
    public static List<String> managedProductId = new LinkedList<>();

    public void additemId(String titleno) {
        CartManager.managedProductId.add(titleno);
    }

    public void deleteitemId(String itemId) {
        CartManager.managedProductId.remove(new String(itemId));
    }
}
