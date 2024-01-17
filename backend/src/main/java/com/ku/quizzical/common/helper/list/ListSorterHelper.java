package com.ku.quizzical.common.helper.list;

import java.util.Comparator;
import java.util.List;
import com.ku.quizzical.common.helper.ComparatorHelper;
import com.ku.quizzical.common.util.list.ListInsertionSorter;
import com.ku.quizzical.common.util.list.ListMergeSorter;
import com.ku.quizzical.common.util.list.ListQuickSorter;

/**
 * Helper class for List Sorting Operations
 */
public final class ListSorterHelper {
    /**
     * Insertion Sorts a List
     */
    public static <T extends Comparable<? super T>> void insertionSort(List<T> list) {
        ListSorterHelper.insertionSort(list, ComparatorHelper::compare);
    }

    /**
     * Insertion Sorts a List
     */
    public static <T> void insertionSort(List<T> list, Comparator<T> comparator) {
        ListSorterHelper.insertionSort(list, 0, list.size(), comparator);
    }

    /**
     * Insertion Sorts a List
     */
    public static <T> void insertionSort(List<T> list, int start, int upTo,
            Comparator<T> comparator) {
        ListInsertionSorter.newInstance().sort(list, start, upTo, comparator);
    }

    /**
     * Insertion Sorts a List
     */
    public static <T extends Comparable<? super T>> void mergeSort(List<T> list) {
        ListSorterHelper.mergeSort(list, ComparatorHelper::compare);
    }

    /**
     * Insertion Sorts a List
     */
    public static <T> void mergeSort(List<T> list, Comparator<T> comparator) {
        ListSorterHelper.mergeSort(list, 0, list.size(), comparator);
    }

    /**
     * Insertion Sorts a List
     */
    public static <T> void mergeSort(List<T> list, int start, int upTo, Comparator<T> comparator) {
        ListMergeSorter.newInstance().sort(list, start, upTo, comparator);
    }

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
