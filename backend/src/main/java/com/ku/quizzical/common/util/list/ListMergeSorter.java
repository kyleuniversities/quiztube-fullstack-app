package com.ku.quizzical.common.util.list;

import java.util.Comparator;
import java.util.List;
import com.ku.quizzical.common.helper.list.ListHelper;

/**
 * Utility for performing a merge sort
 */
public final class ListMergeSorter {
    // New Instance Method
    public static ListMergeSorter newInstance() {
        return new ListMergeSorter();
    }

    // Constructor Method
    private ListMergeSorter() {
        super();
    }

    // Main Instance Methods
    public <T> void sort(List<T> list, int start, int upTo, Comparator<T> comparator) {
        // Terminate if the sublist is guaranteed to be sorted
        if (start + 2 > upTo) {
            return;
        }

        // Establishes the mid index
        int mid = (upTo + start) / 2;

        // Sorts the preceding elements
        this.sort(list, start, mid, comparator);

        // Sorts the non-preceding elements
        this.sort(list, mid, upTo, comparator);

        // Merges the two sublists
        this.merge(list, start, mid, upTo, comparator);
    }

    public <T> void merge(List<T> list, int start, int mid, int upTo, Comparator<T> comparator) {
        // Set up sublists
        List<T> preceding = ListHelper.subList(list, start, mid);
        List<T> nonPreceding = ListHelper.subList(list, mid, upTo);

        // Insert elements from each list one at a time
        int i = 0;
        int j = 0;
        int k = start;
        while (i < preceding.size() && j < nonPreceding.size()) {
            // Set up comparison
            int comparison = comparator.compare(preceding.get(i), nonPreceding.get(j));

            // If the preceding element is "less", insert it first
            if (comparison < 0) {
                list.set(k, preceding.get(i));
                i++;
                k++;
                continue;
            }

            // If the preceding element is "not less", insert the
            // non-preceding element
            list.set(k, nonPreceding.get(j));
            j++;
            k++;
        }

        // Insert any remaining elements from the preceding list
        while (i < preceding.size()) {
            list.set(k, preceding.get(i));
            i++;
            k++;
        }

        // Insert any remaining elements from the non-preceding list
        while (j < nonPreceding.size()) {
            list.set(k, nonPreceding.get(j));
            j++;
            k++;
        }
    }
}
