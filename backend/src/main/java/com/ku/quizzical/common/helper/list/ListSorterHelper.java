package com.ku.quizzical.common.helper.list;

import java.util.Comparator;
import java.util.List;
import com.ku.quizzical.common.helper.ComparatorHelper;
import com.ku.quizzical.common.util.list.ListQuickSorter;

/**
 * Helper class for List Sorting Operations
 */
public final class ListSorterHelper {
    /**
     * Quick Sorts a List
     */
    public static <T extends Comparable<? super T>> void quickSort(List<T> list) {
        ListSorterHelper.quickSort(list, ComparatorHelper::compare);
    }

    /**
     * Quick Sorts a List
     */
    public static <T> void quickSort(List<T> list, Comparator<T> comparator) {
        ListSorterHelper.quickSort(list, 0, list.size(), comparator);
    }

    /**
     * Quick Sorts a List
     */
    public static <T> void quickSort(List<T> list, int start, int upTo, Comparator<T> comparator) {
        ListQuickSorter.newInstance().sort(list, start, upTo, comparator);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ListSorterHelper() {
        super();
    }
}
