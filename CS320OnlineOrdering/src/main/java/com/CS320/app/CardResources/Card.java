package com.CS320.app.CardResources;

import java.util.List;

public class Card {

    private String name;
    
    private boolean foil;
    
    private String tcgplayer_id;
    
    private Prices prices;

    private Card() {
    }
    public void sendNameToLowerCase() {
        this.name = name.toLowerCase();
    }
    public String getName() {
        return name;
    }
    public String getTcgplayer_id() {
        return tcgplayer_id;
    }
    public Prices getPrices() {
        return prices;
    }
    public boolean getFoil() {
        return foil;
    }
    public static Card getCard(String name) {
        Card card = new Card();
        card.name = name.toLowerCase();
        return card;
    }
    // @Override
    // public boolean equals(Object obj) {
    //     Card t = (Card) obj;
    //     return name.equals(t.name);
    // }
}
