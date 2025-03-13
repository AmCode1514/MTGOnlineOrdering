package com.CS320.app.CardResources;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KDTree<T> {
    T[] list;
    List<KDTreeNode<T>>[] sortedLists;
    Comparator<T>[] dimensions;
    KDTreeNode<T>[] builtTree;
    int[] keyIndices;

    // the keying indices are an optional parameter which takes in the index of one
    // or more axes which will be grouped together into node buckets.
    // this allows the tree to maintain buckets of items for which an axis or axes
    // are equal. Trees built with keying indices have no predefined node size,
    // additionally, this makes a nearest neighbor algorithm more complex.
    @SafeVarargs
    public KDTree(T[] list, int[] optionalKeyingIndices, Comparator<T>... dimensions) {
        this.list = list;
        this.dimensions = dimensions;
        keyIndices = optionalKeyingIndices;
        @SuppressWarnings("unchecked")
        KDTreeNode<T>[] tempTree = (KDTreeNode<T>[]) new KDTreeNode[list.length];
        builtTree = tempTree;
        @SuppressWarnings("unchecked")
        List<KDTreeNode<T>>[] tempSortedLists = (List<KDTreeNode<T>>[]) new List[dimensions.length];
        sortedLists = tempSortedLists;

        for (int i = 0; i < dimensions.length; ++i) {
            List<KDTreeNode<T>> nodeList = new ArrayList<>();
            final int finalI = i;
            for (T item : list) {
                nodeList.add(new KDTreeNode<>(item));
            }
            // this code is written by AI and suspect, check during testing
            Comparator<KDTreeNode<T>> nodeComparator = (n1, n2) -> dimensions[finalI].compare(n1.get(0), n2.get(0));
            nodeList.sort(nodeComparator);
            sortedLists[i] = nodeList;
        }

        recursiveBuild(sortedLists, 0, 0);
    }

    private void recursiveBuild(List<KDTreeNode<T>>[] subLists, int alternate, int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        if (subLists[0].size() <= 1) {
            if (subLists.length == 0) {
                return;
            }
            builtTree[index] = subLists[0].get(0);
            return;
        }
        int medianIndex;
        KDTreeNode<T> pivotNode;
        Comparator<T> usedComparator = dimensions[alternate % dimensions.length];
        List<KDTreeNode<T>> currentMedianList = subLists[alternate % subLists.length];
        ++alternate;
        medianIndex = getMedianIndex(currentMedianList);
        pivotNode = currentMedianList.get(medianIndex);
        builtTree[index] = pivotNode;
        pivotNode.emplaceNode(index);

        // Create a new array of sublists for the right partition.
        @SuppressWarnings("unchecked")
        List<KDTreeNode<T>>[] rightList = (List<KDTreeNode<T>>[]) new List[subLists.length];
        for (int i = 0; i < subLists.length; ++i) {
            List<KDTreeNode<T>>[] splitSubArrays = splitList(subLists[i], usedComparator, pivotNode);
            subLists[i] = splitSubArrays[0];
            rightList[i] = splitSubArrays[1];
        }
        recursiveBuild(subLists, alternate, leftChild);
        recursiveBuild(rightList, alternate, rightChild);
    }

    // public T findBy

    private List<KDTreeNode<T>>[] splitList(List<KDTreeNode<T>> listToSplit, Comparator<T> t, KDTreeNode<T> pivotNode) {
        List<KDTreeNode<T>> left = new ArrayList<>();
        List<KDTreeNode<T>> right = new ArrayList<>();

        for (int i = 0; i < listToSplit.size(); ++i) {
            if (listToSplit.size() > 1 && keyIndices.length != 0) {
                int numEqualAxes = 0;
                for (int j = 0; j < keyIndices.length; ++j) {
                    if (dimensions[keyIndices[j]].compare(pivotNode.get(0), listToSplit.get(i).get(0)) == 0) {
                        ++numEqualAxes;
                    } else {
                        break;
                    }
                }
                if (numEqualAxes == keyIndices.length) {
                    pivotNode.addElement(listToSplit.get(i).get(0));
                    listToSplit.remove(i);
                    --i;
                }
                numEqualAxes = 0;
            }
            int cmp = t.compare(listToSplit.get(i).itemList.get(0), pivotNode.get(0));
            if (cmp < 0) {
                left.add(listToSplit.get(i));
            }
            if (cmp > 0) {
                right.add(listToSplit.get(i));
            }
        }
        @SuppressWarnings("unchecked")
        List<KDTreeNode<T>>[] splits = (List<KDTreeNode<T>>[]) new List[2];
        splits[0] = left;
        splits[1] = right;
        return splits;
    }

    private int getMedianIndex(List<KDTreeNode<T>> subList) {
        return subList.size() / 2;
    }
    // TODO, implement candidate function, maybe allow a user to define their own
    // function.

    // TODO, implement search function and some optional heuristics for branch
    // pruning. Candidate required.

    //skeleton search algorithm
    public void findCandidates(T reference, KDTreeNode<T> currentNode, int alternate, List<T> accumulatedCandidates) {
       
        if (builtTree[currentNode.leftChild] == null) {
            return;
        }
        // someCandidateDefiningFunction
        if (isCandidate()) {
            if (currentNode.isAggregate()) {

            }
        }
        int comparison = dimensions[alternate % dimensions.length].compare(reference, currentNode.get(0));
        if (comparison < 0) {
            findCandidates(reference, builtTree[currentNode.leftChild], ++alternate, accumulatedCandidates);
        }
        else if (comparison > 0) {
            findCandidates(reference, builtTree[currentNode.rightChild], ++alternate, accumulatedCandidates);
        }
    }
}