package com.CS320.app.misc;

import java.util.ArrayList;

public class Order {
    private ArrayList<String> items;
    private final String email;
    private double price;
    private int numItems;
    public Order(ArrayList<String> items, double price, int numItems, String email) {
        this.items = items;
        this.price = price;
        this.numItems = numItems;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}
