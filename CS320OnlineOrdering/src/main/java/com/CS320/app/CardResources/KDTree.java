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
    Comparator<KDTreeNode<T>>[] dimensions;
    Comparator<T>[] rawDimensions;
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
        rawDimensions = dimensions;
        keyIndices = optionalKeyingIndices;
        @SuppressWarnings("unchecked")
        KDTreeNode<T>[] tempTree = (KDTreeNode<T>[]) new KDTreeNode[list.length + 48930];
        builtTree = tempTree;
        @SuppressWarnings("unchecked")
        List<KDTreeNode<T>>[] tempSortedLists = (List<KDTreeNode<T>>[]) new List[dimensions.length];
        sortedLists = tempSortedLists;
        @SuppressWarnings("unchecked")
        Comparator<KDTreeNode<T>>[] convertedDimensions = (Comparator<KDTreeNode<T>>[]) new Comparator[dimensions.length];
        for (int i = 0; i < dimensions.length; ++i) {
            final int finalI = i;
            convertedDimensions[i] = (n1, n2) -> dimensions[finalI].compare(n1.get(0), n2.get(0));
        }
        for (int i = 0; i < dimensions.length; ++i) {
            List<KDTreeNode<T>> nodeList = new ArrayList<>();
            for (T item : list) {
                nodeList.add(new KDTreeNode<>(item));
            }
            nodeList.sort(convertedDimensions[i]);
            sortedLists[i] = nodeList;
        }
        this.dimensions = convertedDimensions;
        recursiveBuild(sortedLists, 0, 0);
    }

    private void recursiveBuild(List<KDTreeNode<T>>[] subLists, int alternate, int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        if (subLists[0].size() <= 1) {
            if (subLists[0].size() == 0) {
                return;
            }
            builtTree[index] = subLists[0].get(0);
            subLists[0].get(0).emplaceNode(index);
            return;
        }
        int medianIndex;
        KDTreeNode<T> pivotNode;
        Comparator<KDTreeNode<T>> usedComparator = dimensions[alternate % dimensions.length];
        List<KDTreeNode<T>> currentMedianList = subLists[alternate % subLists.length];
        ++alternate;
        medianIndex = getMedianIndex(currentMedianList);
        pivotNode = currentMedianList.get(medianIndex);
        builtTree[index] = pivotNode;
        //for some reason nodes in the tree do not have left or right children, this is likely due to an indexing error.
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

    private List<KDTreeNode<T>>[] splitList(List<KDTreeNode<T>> listToSplit, Comparator<KDTreeNode<T>> t,
            KDTreeNode<T> pivotNode) {
        List<KDTreeNode<T>> left = new ArrayList<>();
        List<KDTreeNode<T>> right = new ArrayList<>();
        // something in this logic is breaking the loop invariant for the buildTree
        // recursion. I get an indexing error 0 onto 0 length list, so the subLists are
        // not synchronized.
        aggregate(listToSplit, pivotNode);
        for (int i = 0; i < listToSplit.size(); ++i) {
            int cmp = t.compare(listToSplit.get(i), pivotNode);
            if (cmp < 0) {
                left.add(listToSplit.get(i));
            }
            if (cmp > 0) {
                right.add(listToSplit.get(i));
            }
            // else {
            //     //need to change this, currently will add many duplicates to the tree.
            //     left.add(listToSplit.get(i));
            // }
        }
        @SuppressWarnings("unchecked")
        List<KDTreeNode<T>>[] splits = (List<KDTreeNode<T>>[]) new List[2];
        splits[0] = left;
        splits[1] = right;
        return splits;
    }

    public void aggregate(List<KDTreeNode<T>> listToSplit, KDTreeNode<T> pivotNode) {
        for (int i = 0; i < listToSplit.size(); ++i) {
            if (listToSplit.size() > 1 && keyIndices.length != 0) {
                int numEqualAxes = 0;
                for (int j = 0; j < keyIndices.length; ++j) {
                    if (dimensions[keyIndices[j]].compare(pivotNode, listToSplit.get(i)) == 0) {
                        ++numEqualAxes;
                    } else {
                        break;
                    }
                }
                if (numEqualAxes == keyIndices.length) {
                    pivotNode.addElement(listToSplit.get(i).get(0));
                    listToSplit.remove(i);
                    i = Math.max(i - 1, 0);
                }
            }
        }
    }

    private int getMedianIndex(List<KDTreeNode<T>> subList) {
        return subList.size() / 2;
    }
    // TODO, implement candidate function, maybe allow a user to define their own
    // function.

    // TODO, implement search function and some optional heuristics for branch
    // pruning. Candidate required.

    // important note, this candidates function assumes that there is at least one
    // fully qualified axis in the reference.
    // For cases where this isn't true, there will have to be a mechanism to flag
    // branches as possibly containing a candidate.
    // This is a much more difficult task with a great deal of nuance and
    // understanding of both the underlying data and axes which have been iterated
    // over.
    // In the current implementation, I define a CandidateFunction which is then
    // used to accumulate candidates based on values of the reference and the
    // current node.
    // In the future, I will define another function which based on some tracked
    // data whether or not a branch at a given node may contain candidates.
    // For my current use case, with only strings, given an input of "k", you will
    // never have to consider branches with nodes that have characters after k.
    // And since my other axis is fully qualified, my actually candidates always
    // fall to one side of the tree. Though this may not always be true in other
    // use-cases.
    public void find(T reference, List<T> accumulatedCandidates, CandidateFunction<T> funct) {
        recursiveCandidates(reference, builtTree[0], 0, accumulatedCandidates, funct);
    }
    public void recursiveCandidates(T reference, KDTreeNode<T> currentNode, int alternate, List<T> accumulatedCandidates,
            CandidateFunction<T> funct) {

        if (currentNode == null) {
            return;
        }
        // someCandidateDefiningFunction
        if (currentNode.isAggregate()) {
            for (T value : currentNode) {
                if (funct.isCurrentNodeCandidate(alternate, reference, value)) {
                    accumulatedCandidates.add(value);
                }
            }
        } else {
            if (funct.isCurrentNodeCandidate(alternate, reference, currentNode.get(0))) {
                accumulatedCandidates.add(currentNode.get(0));
            }
        }
        //dummy reference
        KDTreeNode<T> dummyReference = new KDTreeNode<T>(reference);
        int comparison = dimensions[alternate % dimensions.length].compare(dummyReference,
                currentNode);
        if (comparison < 0) {
            if (currentNode.leftChild > builtTree.length) {
                return;
            }
            recursiveCandidates(reference, builtTree[currentNode.leftChild], alternate++,
                    accumulatedCandidates, funct);
        } else if (comparison > 0) {
            if (currentNode.rightChild > builtTree.length) {
                return;
            }
            recursiveCandidates(reference, builtTree[currentNode.rightChild], alternate++,
                    accumulatedCandidates, funct);
        }
        // else {
        //     recursiveCandidates(reference, builtTree[currentNode.leftChild], alternate++, accumulatedCandidates, funct);
        // }
    }
}