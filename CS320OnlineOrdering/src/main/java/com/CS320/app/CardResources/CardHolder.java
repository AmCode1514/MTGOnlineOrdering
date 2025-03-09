package com.CS320.app.CardResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CardHolder {

    private List<Card> cards;
    private transient HashMap<String, ListBlock> prefixMap;
    private transient HashMap<String, Card> hmacMap;
    private transient Card[] searchArray;
    private transient boolean initialized = false;

    @JsonCreator
    //Cardholder is instantiated by Jackson and executes a sort and then a two character prefix mapping of the resultant list. This allows easy indexing to relevant sections of the array. 
    //Additionally, each of the prefixes are bound to a block, this allows for the easy use of the inbuilt binary search function in arrays. 
    private CardHolder() {
    }
    //invoked after deserialization to set up object.
    public void init(byte[] key) {
      if (initialized) {
        return;
      }
      hmacMap = new HashMap<>();
      for (int i = 0; i < cards.size(); ++i) {
        cards.get(i).sendNameToLowerCase();
        if (cards.get(i).getName().length() == 1 || cards.get(i).getTcgplayer_id() == null) {
          cards.remove(i);
        }
        else {
          try {
            hmacMap.put(cards.get(i).getHMACHash(key), cards.get(i));
            
          }
          catch(Exception e) {
            e.printStackTrace();
          }
          
        }
      }
      Collections.sort(cards, new CardComparator());
      ListBlock last = new ListBlock(0);
      prefixMap = new HashMap<>();
      for (int i = 0; i < cards.size(); ++i) {
        // if (getCardNameAtIndex(i).length() == 1) {
        //   continue;
        // } 
        if (!prefixMap.containsKey(getCardNameAtIndex(i).substring(0, 2).toLowerCase())) {
          ListBlock curr = new ListBlock(i);
          prefixMap.put(getCardNameAtIndex(i).substring(0,2).toLowerCase(), curr);
          last.setEndIndex(i - 1);
          last = curr;
        }
      }
      last.setEndIndex(cards.size() - 1);
      searchArray = cards.toArray(new Card[0]);
      initialized = true;
    }
    public List<Card> getCards() {
      return cards;
    }

    //gets the card name at the specified index
    public String getCardNameAtIndex(int index) {
      return cards.get(index).getName();
    }

    public Card getCardFromOrderRequest(Card card, byte[] key) throws CardValidationException {
      //the following line throws an exception only if the name or tcg id is missing
      Card t = hmacMap.get(card.getHMACHash(key));
      if (t == null) {
        //this is the case in which a security violation likely occured, there is no addendum and the context will be injected into the exception.
        throw new CardValidationException("");
      }
      return t;
    }
    //This find function defines a block of cards to look at based on the prefix mapping, and then performs a binary search over it. Some prefixes my have 100s or thousands of elements. Thus, this improves search speed for such cases
    //This function implements a three way hybrid approach of prefix mapping, linear, and binary search. Linear search improves performance for very small blocks.
    public Card[] find(String name) {
      name = name.toLowerCase();
      ArrayList<Card> clientCards = new ArrayList<>();
      ListBlock blockByPrefix = prefixMap.get(name.substring(0, 2));
      if (blockByPrefix == null) {
        return new Card[0];
      }
      if (blockByPrefix.endIndex - blockByPrefix.startIndex <= 16) {
        linearSearchForSmallBlocks(clientCards, name, blockByPrefix);
        return clientCards.toArray(new Card[0]);
      }
      int matchIndex = bestMatchIndex(searchArray, blockByPrefix, name);
      if (matchIndex >= 0) {
        return new Card[]{searchArray[matchIndex]};
      }
      else {
        matchIndex = -matchIndex - 1;
        while (matchIndex <= blockByPrefix.endIndex && isRemainderEqual(name, getCardNameAtIndex(matchIndex)) && clientCards.size() < 10) {
          clientCards.add(cards.get(matchIndex));
          ++matchIndex;
        }
        return clientCards.toArray(new Card[0]);
      }
    }
    //this helper function performs the binary search using the inbuilt binary search array algorithm. end indices are exclusive which is why 1 is added.
    private int bestMatchIndex(Card[] search, ListBlock block, String name) {
      int index = Arrays.binarySearch(search, block.startIndex, block.endIndex + 1, Card.getCard(name), new CardComparator());
      return index;
    }

    //simple linear scan over matching prefixes in the small block, improves performance in cases where binary search calls use more memory and processing for a small list.
    private void linearSearchForSmallBlocks(ArrayList<Card> clientCards, String match, ListBlock block) {
      int startingIndex = block.startIndex;
      while(startingIndex <= block.endIndex && isRemainderEqual(match, getCardNameAtIndex(startingIndex)) && clientCards.size() < 10) {
        clientCards.add(cards.get(startingIndex));
        ++startingIndex;
      }
    }

    //this function tests if the remainder of the string, aside from the two character prefix, are equal.
    private boolean isRemainderEqual(String match, String card) {
      //thinking about it, you might actually be able to manipulate the card string and use a hashing algorithm to achieve O(1)
      int minLength = Math.min(match.length(), card.length());
      for (int i = 2; i < minLength; ++i) {
          if (match.charAt(i) != card.charAt(i)) {
              return false;
          }
      }
      return true;
    }

  
}


  