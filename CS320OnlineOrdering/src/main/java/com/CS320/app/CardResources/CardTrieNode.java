package com.CS320.app.CardResources;

import java.util.ArrayList;
import java.util.List;

public class CardTrieNode {
    private final CardTrieNode[] nodes = new CardTrieNode[51];
    private final Character associatedCharacter;
    private final List<Card> associatedCards;
    public static final List<Character> unknownCharacters = new ArrayList<>();
    private int numWords;
    private int numCardsInBranch;
    private int numChildNodes;
    public CardTrieNode(Character associatedChar) {
       // nodes[CardTrieNode.determineIndex(associatedChar)] = new CardTrieNode(associatedChar);
        associatedCharacter = associatedChar;
        associatedCards = new ArrayList<>();
        numWords = 0;
        numChildNodes = 0;
    }
    public CardTrieNode tryAdd(Character charToBeAdded) {
        int nodeIndex = CardTrieNode.determineIndex(charToBeAdded);
        CardTrieNode node = nodes[nodeIndex];
        ++numCardsInBranch;
        if(node == null) {
            node = new CardTrieNode(charToBeAdded);
            ++numChildNodes;
            nodes[nodeIndex] = node;
            return node;
        }
        return node;
    }
    //method can return null if character doesn't exist
    public CardTrieNode getNode(Character selectingCharacter) {
        int nodeIndex = CardTrieNode.determineIndex(selectingCharacter);
        return nodes[nodeIndex];
    }
    public CardTrieNode[] getNodeList() {
        return nodes;
    }
    public void addCard(Card card) {
        associatedCards.add(card);
        ++numWords;
    }

    public Card removeCard(int index) {
        --numWords;
        return associatedCards.remove(index);
    }

    public List<Card> getCardList() {
        return associatedCards;
    }
    public int getNumCards() {
        return numWords;
    }

    public int getNumCardsInBranch() {
        return numCardsInBranch;
    }

    public int getNumChildNodes() {
        return numChildNodes;
    }
//this function is highly convoluted
    public static int determineIndex(Character associatedChar) {
        int asciiValue = ((int) associatedChar.charValue()) - 19;
        if (asciiValue >= 78 && asciiValue <= 103) {
            return asciiValue % 26;
        }
        
        else if (asciiValue == 13) {
            return 26;
        }
        else if (asciiValue == 20) {
            return 27;
        }
        else if (asciiValue == 25) {
            return 28;
        }
        else if (asciiValue == 24) {
            return 29;
        }
        else if (asciiValue == 15) {
            return 30;
        }
        else if (asciiValue >= 29 && asciiValue <= 38) {
            return asciiValue + 2;
        }
        else if (asciiValue == 76) {
            return 41;
        }
        else if (asciiValue == 26) {
            return 42;
        }
        else if (asciiValue == 28) {
            return 43;
        }
        else if (asciiValue == 27) {
            return 44;
        }
        else if (asciiValue == 39) {
            return 45;
        }
        else if (asciiValue == 21) {
            return 46;
        }
        else if (asciiValue == 22) {
            return 47;
        }
        else if(asciiValue == 14) {
            return 48;
        }
        else if (asciiValue == 19) {
            return 49;
        }
        else if (asciiValue == 40) {
            return 50;
        }
        unknownCharacters.add(associatedChar);
        return -1;
    }
}
