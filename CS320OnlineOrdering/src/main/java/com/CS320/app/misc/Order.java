package com.CS320.app.misc;

import java.util.ArrayList;

public class Order {
    private ArrayList<String> items;
    private String customerName;
    private double price;
    private int numItems;
    public Order(ArrayList<String> items, double price, int numItems) {
        this.items = items;
        this.price = price;
        this.numItems = numItems;
    }
}
