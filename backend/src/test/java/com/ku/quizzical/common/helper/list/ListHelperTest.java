package com.ku.quizzical.common.helper.list;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.ListHelper;

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
}
