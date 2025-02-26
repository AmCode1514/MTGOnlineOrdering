package com.CS320.app.CardResources;

import java.io.IOException;
import java.util.logging.Logger;

public class CardListAccessor {

    private CardHolder holder;
    private ScryFallParser parser;

    public CardListAccessor() {
        try {
            parser = new ScryFallParser();
            holder = parser.parseFromJSONAndSort();
        }
        catch(IOException e) {
            e.printStackTrace();
            //System.exit(1);
        }
    }

    public Card[] getCards(String name) throws IllegalStateException{
        if (holder == null) {
            throw new IllegalStateException("Card List is uninitialized, improper startup execution likely");
        }
        return holder.find(name);
    }

    public void parseAndUpdateCardList() throws IOException {
        try {
            holder = parser.parseFromJSONAndSort();
        }
        catch(IOException e) {
            throw e;
        }
    }
}
