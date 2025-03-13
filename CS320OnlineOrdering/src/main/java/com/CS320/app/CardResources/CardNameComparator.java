package com.CS320.app.CardResources;

import java.util.Comparator;

public class CardNameComparator implements Comparator<Card>{

    @Override
    public int compare(Card o1, Card o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
    }
    
}
