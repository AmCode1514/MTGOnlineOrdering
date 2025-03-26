package com.CS320.app.CardResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
                    return;
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
        String originalUnadulteratedName = current.getCardList().get(0).getName();
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
    //TODO, build back tracking find
    public List<Card> find(Byte matchingMask, String name, String set, int maxDepth) {
        List<Card> cardList = new ArrayList<>();
        CardTrieNode currentNode = root.getNode(name.charAt(0));
        if (currentNode == null || name.length() < 2) {
            return cardList;
        }
        //first step, navigate down tree until we reach a node which has either no children, or there is no next character in the name string.
        for (int i = 1; i < name.length(); ++i) {
            if (currentNode.getNumChildNodes() == 0 || currentNode.getNode(name.charAt(i)) == null) {
                break;
            }
            currentNode = currentNode.getNode(name.charAt(i));
        }
        //first, all exact name entries should give the correct node for the matches, so check them first.
        for (Card cardAtNode : currentNode.getCardList()) {
            if (isMatch(set, matchingMask, cardAtNode) && cardList.size() < 10) {
                cardList.add(cardAtNode);
            }
        }
        if (currentNode.getNumChildNodes() != 0) {
            recursiveDepthSearch(cardList, set, matchingMask, currentNode, maxDepth, 0);
        }
        return cardList;
    }

    private boolean isMatch(String set, Byte matchingMask, Card currentCard) {
        if (((byte)(currentCard.getMask() & matchingMask)) == matchingMask && currentCard.getset_name().equals(set)) {
            return true;
        }
        return false;
    }

    private void recursiveDepthSearch(List<Card> cardList, String set, Byte matchingMask, CardTrieNode startNode, int maxDepth, int currentDepth) {
        if (cardList.size() == 10 || startNode.getNumChildNodes() == 0 || currentDepth == maxDepth) {
            return;
        }
        CardTrieNode[] originalNodesList = startNode.getNodeList();
        for (int i = 0; i < originalNodesList.length; ++i) {
            if (originalNodesList[i] != null) {
                CardTrieNode examinedNode = originalNodesList[i];
                boolean hasCards = false;
                if (examinedNode.getNumCards() > 0) {
                    hasCards = true;
                    for (Card t : examinedNode.getCardList()) {
                        if (cardList.size() < 10 && isMatch(set, matchingMask, t)) {
                            cardList.add(t);
                        }
                    }
                }
                if (hasCards) {
                    recursiveDepthSearch(cardList, set, matchingMask, examinedNode, maxDepth, currentDepth + 1);
                }
                else {
                    recursiveDepthSearch(cardList, set, matchingMask, examinedNode, maxDepth, currentDepth);
                }
            }
        }
    }
}
