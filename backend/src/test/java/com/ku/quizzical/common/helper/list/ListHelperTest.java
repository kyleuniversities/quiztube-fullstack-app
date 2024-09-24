package com.ku.quizzical.common.helper.list;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.RandomHelper;

/**
 * Test Class for List Helper
 */
@SpringBootTest
@ActiveProfiles("test")
public class ListHelperTest {
    @Test
    void binarySearchTest() throws Exception {
        final int SIZE = 20;
        List<Integer> indexList = ListHelper.newIndexList(SIZE);
        IterationHelper.forEach(-3 * SIZE, 3 * SIZE, (Integer i) -> {
            int matchingIndex = ListHelper.indexOfWithBinarySearch(indexList, i);
            int expectedIndex = ConditionalHelper.ifReturnElse(i >= 0 && i < SIZE, i, -1);
            assertThat(matchingIndex).isEqualTo(expectedIndex);
        });
    }

    @Test
    void quickSortTest() throws Exception {
        this.sortTest(ListSorterHelper::quickSort);
    }

    @Test
    void insertionSortTest() throws Exception {
        this.sortTest(ListSorterHelper::insertionSort);
    }

    @Test
    void mergeSortTest() throws Exception {
        this.sortTest(ListSorterHelper::mergeSort);
    }

    // Private Helper Methods
    private void sortTest(Consumer<List<Integer>> sorter) throws Exception {
        final int LIST_SIZE = 30;
        IterationHelper.forEach(100, () -> {
            List<Integer> list = ListHelper.newArrayList();
            IterationHelper.forEach(LIST_SIZE, () -> {
                list.add(RandomHelper.nextInt(40));
            });
            sorter.accept(list);
            IterationHelper.forEach(LIST_SIZE - 1, (Integer i) -> {
                assertThat(list.get(i)).isLessThanOrEqualTo(list.get(i + 1));
            });
        });
    }
}
