package com.CS320.app.CardResources;

import java.util.ArrayList;
import java.util.List;

public class CardTrieNode {
    private final CardTrieNode[] nodes = new CardTrieNode[29];
    private final Character associatedCharacter;
    private final List<Card> associatedCards;
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
    public CardTrieNode tryAdd(Character terminatingCard) {
        int nodeIndex = CardTrieNode.determineIndex(terminatingCard);
        CardTrieNode node = nodes[nodeIndex];
        ++numCardsInBranch;
        if(node == null) {
            node = new CardTrieNode(terminatingCard);
            ++numChildNodes;
            nodes[nodeIndex] = node;
            return node;
        }
        return node;
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

    public static int determineIndex(Character associatedChar) {
        int asciiValue = ((int) associatedChar.charValue()) - 10;
        if (asciiValue >= 84 && asciiValue <= 109) {
            return asciiValue % 29;
        }
        else if (asciiValue == 39) {
            return 26;
        }
        else if (asciiValue == 44) {
            return 27;
        }
        else if (asciiValue == 43) {
            return 28;
        }
        return -1;
    }
}
