package com.ku.quizzical.common.util.list;

import java.util.Comparator;
import java.util.List;

/**
 * Utility for performing a insertion sort
 */
public final class ListInsertionSorter {
    // New Instance Method
    public static ListInsertionSorter newInstance() {
        return new ListInsertionSorter();
    }

    // Constructor Method
    private ListInsertionSorter() {
        super();
    }

    // Main Instance Method
    public <T> void sort(List<T> list, int start, int upTo, Comparator<T> comparator) {
        // Terminate if the sublist is guaranteed to be sorted
        if (start + 2 > upTo) {
            return;
        }

        // Iterates through the list, inserting each value
        // in the relatively appropriate index
        for (int i = start + 1; i < upTo; i++) {
            // Set up the insertion value
            T insertionValue = list.get(i);

            // Set up the insertion destination index
            int j = i;

            // Iterate backwards through elements greater than the
            // the insertion value
            while (j > 0 && comparator.compare(list.get(j - 1), insertionValue) > 0) {
                list.set(j, list.get(j - 1));
                j--;
            }

            // Insert the insertion value before all the elements that
            // were found to be "greater" than the insertion value
            list.set(j, insertionValue);
        }
    }
}
