package com.CS320.app.CardResources;

public class CardTrie {
    CardTrieRoot root;

    public CardTrie(Card[] listOfCards) {
        root = new CardTrieRoot();
        for (Card t : listOfCards) {
            addCard(t);
        }
    }

    public void addCard(Card cardToBeAdded) {
        String name = cardToBeAdded.getName();
        CardTrieNode current = root.tryAdd(name.charAt(0));
        for (int i = 1; i < name.length(); ++i) {
            if (current.getNumChildNodes() == 0 && current.getNumCards() == 0) {
                current.addCard(cardToBeAdded);
                return;
            } else if (current.getNumCards() >= 1) {
                if (isCardAtNodeEqual(current, name)) {
                    current.addCard(cardToBeAdded);
                } else {
                    if (current.getNumCards() == 1) {
                        splitNode(current, name, i, cardToBeAdded);
                        return;
                    }
                }
            }
            current = current.tryAdd(name.charAt(i));
        }
        current.addCard(cardToBeAdded);
    }

    private boolean isCardAtNodeEqual(CardTrieNode current, String name) {
        if (current.getCardList().get(0).getName().equals(name)) {
            return true;
        }
        return false;
    }

    private void splitNode(CardTrieNode current, String name, int index, Card addingCard) {
        String nameOfCurrentCard = current.getCardList().get(0).getName().substring(index);
        name = name.substring(index);
        Card originalCardAtNode = current.removeCard(0);
        int shortestLength = Math.min(name.length(), nameOfCurrentCard.length());
        int i = 0;
        while (i < shortestLength && name.charAt(i) == nameOfCurrentCard.charAt(i)) {
            current = current.tryAdd(name.charAt(i));
            ++i;
        }
        if (i == shortestLength) {
            if (name.length() == shortestLength) {
                current.addCard(addingCard);
                current.tryAdd(nameOfCurrentCard.charAt(i)).addCard(originalCardAtNode);
            } else {
                current.addCard(originalCardAtNode);
                current.tryAdd(name.charAt(i)).addCard(addingCard);
            }
        } else {
            CardTrieNode left = current.tryAdd(name.charAt(i));
            CardTrieNode right = current.tryAdd(nameOfCurrentCard.charAt(i));
            left.addCard(addingCard);
            right.addCard(originalCardAtNode);

        }

    }
}
