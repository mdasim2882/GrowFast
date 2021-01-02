package com.example.growfast.HelperMethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CartManager {
    public static List<String> managedProductId = new LinkedList<>();
    public static List<String> managedCollectionName = new ArrayList<>();

    public void additemId(String titleno) {
        String t = titleno.trim();

        if (!CartManager.managedProductId.contains(t)) {
            CartManager.managedProductId.add(t);
        }

    }

    public void deleteitemId(String itemId) {
        CartManager.managedProductId.remove(new String(itemId));
    }
}
