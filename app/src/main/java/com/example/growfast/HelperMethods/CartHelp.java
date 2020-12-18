package com.example.growfast.HelperMethods;

public class CartHelp {
    String itemName;
    String itemprice, itemType, itemID;

    public CartHelp() {
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public CartHelp(String itemName, String itemprice, String itemType, String itemID) {
        this.itemName = itemName;
        this.itemprice = itemprice;
        this.itemType = itemType;
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
