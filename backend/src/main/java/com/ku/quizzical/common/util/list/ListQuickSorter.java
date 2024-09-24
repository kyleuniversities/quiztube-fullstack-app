package com.ku.quizzical.common.util.list;

import java.util.Comparator;
import java.util.List;
import com.ku.quizzical.common.helper.list.ListHelper;

/**
 * Utility for performing a quick sort
 */
public final class ListQuickSorter {
    // New Instance Method
    public static ListQuickSorter newInstance() {
        return new ListQuickSorter();
    }

    // Constructor Method
    private ListQuickSorter() {
        super();
    }

    // Main Instance Method
    public <T> void sort(List<T> list, int start, int upTo, Comparator<T> comparator) {
        // Terminate if the sublist is guaranteed to be sorted
        if (start + 2 > upTo) {
            return;
        }

        // Set up pivot value
        int pivotValueIndex = (start + upTo) / 2;
        T pivotValue = list.get(pivotValueIndex);

        // Swap the pivot element to the end of the sub list
        ListHelper.swap(list, pivotValueIndex, upTo - 1);

        // Separate elements that are "greater" and "not greater" than
        // the pivot value
        int separatingIndex = upTo - 1;
        for (int i = start; i < separatingIndex; i++) {
            // Set up comparison to pivot value
            int comparison = comparator.compare(list.get(i), pivotValue);

            // Move the element to the end if it is "not lesser" than the
            // pivot value
            if (comparison > 0) {
                ListHelper.swap(list, i, separatingIndex - 1);
                separatingIndex--;
                i--;
            }
        }

        // Make the pivot value to be at the separating index
        ListHelper.swap(list, separatingIndex, upTo - 1);

        // Sort elements "not greater" than the pivot value
        this.sort(list, start, separatingIndex, comparator);

        // Sort elements "not greater" than the pivot value
        this.sort(list, separatingIndex + 1, upTo, comparator);
    }
}
