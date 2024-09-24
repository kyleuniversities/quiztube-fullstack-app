package com.ku.quizzical.common.util.list;

import java.util.List;
import java.util.function.Function;

/**
 * Utility for performing a binary search
 */
public final class ListBinarySearcher {
    // New Instance Method
    public static ListBinarySearcher newInstance() {
        return new ListBinarySearcher();
    }

    // Constructor Method
    private ListBinarySearcher() {
        super();
    }

    // Main Instance Method
    public <T> int search(List<T> list, int start, int upTo, Function<T, Integer> comparator) {
        // Return -1 if no search space
        if (start + 1 > upTo) {
            return -1;
        }

        // Set up key constants
        int midIndex = (start + upTo) / 2;
        T midValue = list.get(midIndex);

        // Set up comparison
        int comparison = comparator.apply(midValue);

        // Searches the lower list if the target has lower ordinality
        // than the mid value
        if (comparison < 0) {
            return this.search(list, start, midIndex, comparator);
        }

        // Searches the upper list if the target has upper ordinality
        // than the mid value
        if (comparison > 0) {
            return this.search(list, midIndex + 1, upTo, comparator);
        }

        // Returns the middle index if the target is the mid value
        return midIndex;
    }
}
