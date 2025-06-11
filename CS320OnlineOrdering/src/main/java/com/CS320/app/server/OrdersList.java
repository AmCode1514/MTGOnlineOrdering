package com.CS320.app.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.CS320.app.CardResources.Card;
import com.CS320.app.misc.CardPriceTCG;
import com.CS320.app.misc.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrdersList extends Thread {
    private final Map<String, List<Order>> ordersMap = new HashMap<String, List<Order>>();
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
        //this will need more extensive logging aspects.
        if (ordersMap.get(email) != null) {
            List<Order> customerOrders = ordersMap.get(email);
            for (int i = 0; i < customerOrders.size(); ++i) {
                if (customerOrders.get(i).getOrderNumber() == orderNumber) {
                    customerOrders.remove(i);
                    return;
                }
            }
        }
    }
    public boolean queueOrder(String email, int orderNumber) {
        Order desiredOrder = getOrder(email, orderNumber);
        if (desiredOrder != null) {
            priceQueue.offer(desiredOrder);
            return true;
        }
        return false;
    }
    //return null if an order with a non-matching email and orderNumber
    public synchronized Order getOrder(String email, int orderNumber) {
        if (ordersMap.get(email) != null) {
            List<Order> customerOrders = ordersMap.get(email);
            for (int i = 0; i < customerOrders.size(); ++i) {
                if (customerOrders.get(i).getOrderNumber() == orderNumber) {
                    return customerOrders.get(i);
                }
            }
        }
        return null;
    }
    public synchronized Map<String, List<Order>> getAllOrders() {
        return ordersMap;
    }

    public synchronized List<Order> getAllOrdersFromEmail(String email) {
        return ordersMap.get(email);
    }

    @Override
    public void run() {
        while(true) {
            //I will likely have to manually log errors here since I queue jobs and return a response telling the user the job is started.
            try {
              priceOrder(priceQueue.take());
              Thread.sleep(4000);
            }
            //I would never expect this to be interrupted, this could cause issues later on.
            catch(Exception e) {
                e.printStackTrace();
            }
            
        }
    }

    public void priceOrder(Order order) throws MalformedURLException, IOException, Exception{
        //more work needs to be done, specifically the foil flags must be updated accordingly and individual prices stored in the order.
        double accumulatedPrice = 0;
        int i = 0;
        for (Card orderedCard : order.getCardList()) {
            try {
                URL tcgURL = URI.create(String.format("https://mpapi.tcgplayer.com/v2/product/%s/pricepoints", orderedCard.getTcgplayer_id())).toURL();
                URLConnection conn = tcgURL.openConnection();
                Charset encoding = Charset.defaultCharset();
                String requestEncoding = conn.getContentEncoding();
                if (requestEncoding != null) {
                    encoding = Charset.forName(requestEncoding);
                }
                try(InputStream stream = conn.getInputStream()) {
                    BufferedReader wrappedReader = new BufferedReader(new InputStreamReader(stream, encoding));
                    String json;
                    StringBuilder builder = new StringBuilder();
                    while ((json = wrappedReader.readLine()) != null) {
                        builder.append(json);
                    }
                    boolean foilFlagAtIndex = order.getFoilFlagAtIndex(i);
                    //this code corrects the case that the user somehow has requested a foil for a card which doesn't have a foil printing.
                    if (!(foilFlagAtIndex == true && orderedCard.getfoil())) {
                        foilFlagAtIndex = false;
                    }
                    Double priceValue = parseTCGPrice(builder.toString(), foilFlagAtIndex);
                    order.addPrice(priceValue);
                    ++i;
                    if (priceValue == null) {
                        throw new Exception("priceValue is null, json failed to parse properly");
                    }
                    accumulatedPrice += priceValue;
                    //now process JSON and extract double.
                }
                order.setCurrentTCGPricing(accumulatedPrice);
                order.fulfillOrder();
            }
            catch(MalformedURLException e) {
                throw e;
            }
            catch(IOException e) {
                throw e;
            }
        }
    }
    private Double parseTCGPrice(String json, boolean wantsFoil) throws JsonMappingException, JsonProcessingException {
        ObjectMapper map = new ObjectMapper();
        try {
            List<CardPriceTCG> prices = map.readValue(json, new TypeReference<List<CardPriceTCG>>(){});
            for (CardPriceTCG price : prices) {
                if (wantsFoil) {
                    if (price.getPrintingType().equals("Foil")) {
                        return price.getListedMedianPrice();
                    }
                    
                }
                else {
                    if (price.getPrintingType().equals("Normal")) {
                        return price.getListedMedianPrice();
                    }
                }
            }
            return null;
        }
        catch(JsonMappingException e) {
            throw e;
        }
        catch(JsonProcessingException e) {
            throw e;
        }
    }
}
