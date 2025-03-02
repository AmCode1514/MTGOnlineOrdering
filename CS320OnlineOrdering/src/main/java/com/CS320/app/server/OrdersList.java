package com.CS320.app.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.CS320.app.misc.Order;

public class OrdersList {
    private final HashMap<String, Order> ordersMap = new HashMap<String, Order>();
    private final ArrayList<Order> orders = new ArrayList<Order>();
    public OrdersList() {
    }
    public void add(Order order) {
        orders.add(order);
        ordersMap.put(order.getEmail(), order);
    }
    public void removeOrder(String email, int orderNumber) {
        //linear search array, I dont expect a thousand orders. As such, binary search and quicksort solutions make zero sense.
        //for(int i = 0; i <)


    }
}
