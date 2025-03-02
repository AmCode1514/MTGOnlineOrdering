package com.CS320.app.CardResources;

import java.util.List;

public class Card {

    private String name;
    //this field is not as useful as I thought. It turns out that cards on tcgplayer, if they have a non-unique foil, share the same tcgplayer id. This makes my analysis tricky
    private String tcgplayer_id;
    
    private Prices prices;

    private String set_name;

    private String frame;

    private String full_art;
    //promos have separate tcgplayer ids. This is very useful.
    private boolean promo;

    private boolean textless;

    private String rarity;

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
    public String getSet() {
        return set_name;
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
