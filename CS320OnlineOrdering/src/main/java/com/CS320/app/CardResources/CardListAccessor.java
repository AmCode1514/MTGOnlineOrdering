package com.CS320.app.CardResources;

import java.io.IOException;
import java.util.logging.Logger;

public class CardListAccessor {

    private static CardHolder holder;
    static {
        try {
            holder = ScryFallParser.getScryFallParser().parseFromJSONAndSort();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private CardListAccessor() {

    }

    public static Card[] getCards(String name) throws IllegalStateException{
        if (holder == null) {
            throw new IllegalStateException("Card List is uninitialized, improper startup execution likely");
        }
        return holder.find(name);
    }

    public static void parseAndUpdateCardList() throws IOException {
        try {
            holder = ScryFallParser.getScryFallParser().parseFromJSONAndSort();
        }
        catch(IOException e) {
            throw e;
        }
    }
}
