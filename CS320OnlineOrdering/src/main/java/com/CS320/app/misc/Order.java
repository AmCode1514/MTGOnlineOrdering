package com.CS320.app.misc;
import java.util.ArrayList;
import java.util.List;

import com.CS320.app.CardResources.Card;

public class Order {
    private static int currOrderNumber = 0;
    private List<Card> items;
    private List<Double> cardPricesFromTCG;
    private boolean[] foilFlags;
    private final String email;
    private int numItems;
    private int orderID;
    private double currentTCGPricing = 0.0;
    private boolean isFulfilled = false;
    public Order(List<Card> items, int numItems, String email, boolean[] foilFlags) {
        this.items = items;
        this.numItems = numItems;
        this.email = email;
        this.orderID = currOrderNumber;
        this.foilFlags = foilFlags;
        cardPricesFromTCG = new ArrayList<Double>();
        ++currOrderNumber;
    }
    public String getEmail() {
        return email;
    }
    public void addPrice(Double price) {
        cardPricesFromTCG.add(price);
    }
    public int getOrderNumber() {
        return orderID;
    }
    public List<Card> getCardList() {
        return items;
    }
    public boolean getFoilFlagAtIndex(int index) {
        //can throw out of bounds exception
        return  foilFlags[index];
    }
    public void setCurrentTCGPricing(double price) {
        this.currentTCGPricing = price;
    }
    public void fulfillOrder() {
        isFulfilled = true;
    }
}
