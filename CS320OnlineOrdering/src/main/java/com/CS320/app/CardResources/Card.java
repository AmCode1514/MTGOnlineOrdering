package com.CS320.app.CardResources;

import java.util.List;

public class Card {

    private String name;
    
    private boolean foil;
    
    private String tcgplayer_id;
    
    private Prices prices;

    public Card(String name) {
        this.name = name;
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
}
