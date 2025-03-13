package com.CS320.app.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.CS320.app.misc.Order;

public class OrdersList extends Thread {
    private final HashMap<String, ArrayList<Order>> ordersMap = new HashMap<String, ArrayList<Order>>();
    //1000 is an arbitrary value, in the current use-case there would never be more than a handful
    private final BlockingQueue<Order> priceQueue = new ArrayBlockingQueue<Order>(1000);
    public OrdersList() {
    }
    //This method checks first if an arraylist exists, if not then it instantiates one and adds the order.
    public synchronized void add(Order order) {
        if (ordersMap.get(order.getEmail()) == null) {
            ordersMap.put(order.getEmail(), new ArrayList<>());
            ordersMap.get(order.getEmail()).add(order);
        }
        else {
            ordersMap.get(order.getEmail()).add(order);
        }
    }
    public synchronized void removeOrder(String email, int orderNumber) {
        if (ordersMap.get(email) != null) {
            ArrayList<Order> customerOrders = ordersMap.get(email);
            for (int i = 0; i < customerOrders.size(); ++i) {
                if (customerOrders.get(i).getOrderNumber() == orderNumber) {
                    customerOrders.remove(i);
                    return;
                }
            }
        }
    }
    public void queueOrder(String email, int orderNumber) {
        priceQueue.offer(getOrder(email, orderNumber));
    }
    //return null if an order with a non-matching email and orderNumber
    public synchronized Order getOrder(String email, int orderNumber) {
        if (ordersMap.get(email) != null) {
            ArrayList<Order> customerOrders = ordersMap.get(email);
            for (int i = 0; i < customerOrders.size(); ++i) {
                if (customerOrders.get(i).getOrderNumber() == orderNumber) {
                    return customerOrders.get(i);
                }
            }
        }
        return null;
    }
    public synchronized ArrayList<Order> getAllOrders() {
        ArrayList<Order> combinedList = new ArrayList<>();
        Iterator<ArrayList<Order>> entries = ordersMap.values().iterator();
        while(entries.hasNext()) {
            ArrayList<Order> currCustomerOrderList = entries.next();
            combinedList.addAll(currCustomerOrderList);
        }
        return combinedList;
    }

    public synchronized ArrayList<Order> getAllOrdersFromEmail(String email) {
        return ordersMap.get(email);
    }

    @Override
    public void run() {
        while(true) {
            try {
              priceOrder(priceQueue.take());  
            }
            //I would never expect this to be interrupted, this could cause issues later on.
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }

    public void priceOrder(Order order) {
        //todo, hopefully tcg doesn't ip ban me :)
    }
}
