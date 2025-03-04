package com.CS320.app.CardResources;

import java.io.IOException;
import java.security.SecureRandom;

public class CardListAccessor {

    private CardHolder holder;
    private ScryFallParser parser;
    private final byte[] privateKey = new byte[64];

    public CardListAccessor() {
        try {
            parser = new ScryFallParser();
            new SecureRandom().nextBytes(privateKey);
            parseAndUpdateCardList();
        }
        catch(IOException e) {
            e.printStackTrace();
            //System.exit(1);
        }
        catch(Exception t) {
            t.printStackTrace();
        }
    }

    public Card[] getCards(String name) throws IllegalStateException{
        if (holder == null) {
            throw new IllegalStateException("Card List is uninitialized, improper startup execution likely");
        }
        return holder.find(name);
    }
    //this function validates that a card coming from a users order, is in fact the same as a card that exists in memory on the server
    //this function accomplishes this by using an HMAC algorithm in Java to concat the relevant fields provided and compare with the fields received
    //in theory, someone could craft a custom api request, but they could only get cards that do not have swapped fields. Thus preventing malicious actors reversing prices of named cards with lower priced tcg id'd cards.
    public Card getValidatedCard(Card card) throws CardValidationException {
        return holder.getCardFromOrderRequest(card, privateKey);
    }
    public void parseAndUpdateCardList() throws IOException {
        try {
            holder = parser.parseFromJSONAndSort(privateKey);
        }
        catch(IOException e) {
            throw e;
        }
    }
}
