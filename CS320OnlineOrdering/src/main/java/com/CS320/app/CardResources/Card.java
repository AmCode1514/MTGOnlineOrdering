package com.CS320.app.CardResources;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.text.Normalizer;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Card {

    private String name;
    // this field is not as useful as I thought. It turns out that cards on
    // tcgplayer, if they have a non-unique foil, share the same tcgplayer id. This
    // makes my analysis tricky
    private String tcgplayer_id;

    private Prices prices;

    public String set_name;

    private String frame;

    private boolean full_art;
    // promos have separate tcgplayer ids. This is very useful.
    private boolean promo;

    private boolean textless;

    private String rarity;

    private boolean foil;

    private boolean nonfoil;

    private Byte mask;

    private Card() {

    }

    public void setMask() {
        mask = 0b00000000;
        if (foil == true) {
            mask = (byte) (mask | (1 << 0));
        } 
        if(full_art == true) {
            mask = (byte) (mask | (1 << 1));
        }
        if (promo == true) {
            mask = (byte) (mask | (1 << 2));
        }
    }
    public Byte getMask() {
        return mask;
    }
    public void sendNameToLowerCase() {
        this.name = this.name.toLowerCase();
    }
    public void normalizeName() {
        name = Normalizer.normalize(name, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
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

    public boolean getfull_art() {
        return full_art;
    }
    
    public String getset_name() {
        return set_name;
    }
    public String getframe() {
        return frame;
    }
    public boolean getpromo() {
        return promo;
    }
    public boolean getfoil() {
        return foil;
    }
    public boolean getnonfoil() {
        return nonfoil;
    }
    public static Card getCard(String name, String tcgplayer_id) {
        Card card = new Card();
        card.name = name.toLowerCase();
        card.tcgplayer_id = tcgplayer_id;
        return card;
    }
    //currently unneeded but may be useful later
    public String getHMACHash(byte[] key) throws CardValidationException {
        SecretKeySpec spec = new SecretKeySpec(key, "HmacSHA256");
        try {
            Mac code = Mac.getInstance("HmacSHA256");
            code.init(spec);
            byte[] finishedHashcode = code.doFinal(getClassBytes());
            return new String(finishedHashcode, "UTF-8");
        } catch (CardValidationException e) {
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] getClassBytes() throws CardValidationException {
        if (name == null || tcgplayer_id == null) {
            throw new CardValidationException("Card failed to parse due to missing name or tcg id");
        }
        try {
            byte[] nameBytes = name.getBytes("UTF-8");
            byte[] tcgplayer_idBytes = tcgplayer_id.getBytes("UTF-8");
            byte[] combinedByteArray = new byte[nameBytes.length + tcgplayer_idBytes.length];

            System.arraycopy(nameBytes, 0, combinedByteArray, 0, nameBytes.length);
            System.arraycopy(tcgplayer_idBytes, 0, combinedByteArray, nameBytes.length, tcgplayer_idBytes.length);
            return combinedByteArray;
        } catch (Exception e) {
            e.printStackTrace();
            // this realistically would never happen, if java isn't supporting utf-8 something
            // is seriously wrong with the environment not the application
            return null;
        }
    }
    // @Override
    // public boolean equals(Object obj) {
    // Card t = (Card) obj;
    // return name.equals(t.name);
    // }
}
