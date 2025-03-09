package com.CS320.app.misc;

import java.util.ArrayList;

public class Order {
    private ArrayList<String> items;
    private final String email;
    private double price;
    private int numItems;
    private int orderID;
    private double currentTCGPricing = 0.0;
    private boolean isFulfilled = false;
    public Order(ArrayList<String> items, double price, int numItems, String email) {
        this.items = items;
        this.price = price;
        this.numItems = numItems;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public int getOrderNumber() {
        return orderID;
    }
    public void setCurrentTCGPricing(double price) {
        this.currentTCGPricing = price;
    }
    public void fulfillOrder() {
        isFulfilled = true;
    }
}
