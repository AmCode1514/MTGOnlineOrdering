package com.CS320.app.CardResources;

import java.util.ArrayList;
import java.util.List;

public class CardTrieRoot {
    List<CardTrieNode> characterList;
    private final CardTrieNode[] nodes = new CardTrieNode[29];
    int numCardsInTree = 0;
    public CardTrieRoot() {
        characterList = new ArrayList<>();
    }
    public CardTrieNode tryAdd(Character cha) {
        int index = CardTrieNode.determineIndex(cha);
        if (nodes[index] == null) {
            nodes[index] = new CardTrieNode(cha);
            return nodes[index];
        }
        return nodes[index];
    }
}
