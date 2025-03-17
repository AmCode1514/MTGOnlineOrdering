package com.CS320.app.CardResources;

import java.util.Comparator;

public class CardSetNameComparator implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getset_name(), o2.getset_name());
    }
    
}
