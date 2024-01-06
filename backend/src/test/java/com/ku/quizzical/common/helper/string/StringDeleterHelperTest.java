package com.ku.quizzical.common.helper.string;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test Class for Like Controller
 */
@SpringBootTest
@ActiveProfiles("test")
public class StringDeleterHelperTest {
    @Test
    void deleteAllInstancesTest() throws Exception {
        String text = "The brown fox walked west.  Today is Sunday.";
        String target = "Thebrownfoxwalkedwest.TodayisSunday.";
        assertThat(StringDeleterHelper.deleteAllInstances(text, ' ')).isEqualTo(target);
    }

    @Test
    void deleteLastCharactersTest() throws Exception {
        StringBuilder builder = StringHelper.newBuilder();
        for (int i = 0; i < 7; i++) {
            builder.append(i + ", ");
        }
        StringDeleterHelper.deleteLastCharacters(builder, 2);
        String text = builder.toString();
        String target = "0, 1, 2, 3, 4, 5, 6";
        assertThat(text).isEqualTo(target);
    }
}
