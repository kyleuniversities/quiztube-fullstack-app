package com.ku.quizzical.common.helper.number;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test Class for Like Controller
 */
@SpringBootTest
@ActiveProfiles("test")
public class NumberHelperTest {
    @Test
    void integerHelperTest() throws Exception {
        assertThat(IntegerHelper.absoluteValue(8)).isEqualTo(8);
        assertThat(IntegerHelper.absoluteValue(-8)).isEqualTo(8);
        assertThat(IntegerHelper.greaterThan(7, 3)).isEqualTo(true);
        assertThat(IntegerHelper.lessThan(7, 3)).isEqualTo(false);
        assertThat(IntegerHelper.length(74862)).isEqualTo(5);
        assertThat(IntegerHelper.max(4, 7)).isEqualTo(7);
        assertThat(IntegerHelper.min(4, 7)).isEqualTo(4);
    }

    @Test
    void doubleHelperTest() throws Exception {
        assertThat(DoubleHelper.absoluteValue(8)).isEqualTo(8);
        assertThat(DoubleHelper.absoluteValue(-8)).isEqualTo(8);
        assertThat(DoubleHelper.greaterThan(7, 3)).isEqualTo(true);
        assertThat(DoubleHelper.lessThan(7, 3)).isEqualTo(false);
        assertThat(DoubleHelper.toDecimalText(345.70589, 9)).isEqualTo("345.705890000");
        assertThat(DoubleHelper.toDecimalTextInTenths(1)).isEqualTo("1.0");
        assertThat(DoubleHelper.toDecimalTextInHundredths(17)).isEqualTo("17.00");
        assertThat(DoubleHelper.toDecimalTextInThousandths(16.3)).isEqualTo("16.300");
        assertThat(DoubleHelper.toDecimalTextInMillionths(7)).isEqualTo("7.000000");
    }

    @Test
    void indexHelperTest() throws Exception {
        assertThat(IndexHelper.toIndexText(63, 6)).isEqualTo("000063");
        assertThat(IndexHelper.appendLeadingZeroes(47, 6)).isEqualTo("00000047");
    }

    @Test
    void idHelperTest() throws Exception {
        for (int i = 0; i < 100; i++) {
            assertThat(IdHelper.nextMockId()).isNotEqualTo(IdHelper.nextMockId());
        }
    }
}
