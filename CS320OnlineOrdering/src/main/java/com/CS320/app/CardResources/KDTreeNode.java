package com.CS320.app.CardResources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KDTreeNode<T> implements Iterable<T> {

    boolean isAggregate = false;
    List<T> itemList = new ArrayList<T>();
    int leftChild;
    int rightChild;
    int index;
    public KDTreeNode(T t) {
        itemList.add(t);
        
    }
    public void emplaceNode(int index) {
        leftChild = 2 * index + 1;
        rightChild = 2 * index + 2;
        this.index = index;
    }
    public void addElement(T t) {
        itemList.add(t);
        if (itemList.size() > 1) {
            isAggregate = true;
        }
    }
    public T get(int index) {
        return itemList.get(index);
    }
    public boolean isAggregate() {
        return isAggregate;
    }
    @Override
    public Iterator<T> iterator() {
        return itemList.iterator();
    }
}
