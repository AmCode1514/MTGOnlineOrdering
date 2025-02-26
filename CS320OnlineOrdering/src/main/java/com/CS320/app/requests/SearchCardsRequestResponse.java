package com.CS320.app.requests;

import com.CS320.app.CardResources.Card;

public class SearchCardsRequestResponse extends Response{
    private Card[] cards;
    private int size;
    public SearchCardsRequestResponse(Card[] cards, int size) {
        this.cards = cards;
        this.size = size;
    }
}
