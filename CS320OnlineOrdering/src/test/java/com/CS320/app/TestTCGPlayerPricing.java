package com.CS320.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.CS320.app.CardResources.Card;
import com.CS320.app.misc.Order;
import com.CS320.app.server.OrdersList;

public class TestTCGPlayerPricing {
    @Test
    public void testSingleCardPricing() {
        OrdersList orders = new OrdersList();
        Thread t = orders;
        t.start();
        Card a = Card.getCard("Fury Sliver", "14240");
        List<Card> cards = new ArrayList<Card>();
        cards.add(a);
        boolean[] array = {false};
        Order newOrder = new Order(cards, 1, "brentjones", array);
        try {
        orders.priceOrder(newOrder);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
