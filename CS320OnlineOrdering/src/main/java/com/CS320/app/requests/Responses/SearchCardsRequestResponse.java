package com.CS320.app.requests.Responses;

import com.CS320.app.CardResources.Card;

public class SearchCardsRequestResponse extends Response{
    private Card[] cards;
    private int size;
    public SearchCardsRequestResponse(Card[] cards, int size, String responseType) {
        super(responseType);
        this.cards = cards;
        this.size = size;
    }
}
