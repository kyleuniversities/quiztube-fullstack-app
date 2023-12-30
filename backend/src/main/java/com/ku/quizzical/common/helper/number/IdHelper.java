package com.ku.quizzical.common.helper.number;

import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.RandomHelper;
import com.ku.quizzical.common.helper.TimeHelper;
import com.ku.quizzical.common.helper.string.StringAppenderHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.util.number.MockIdGenerator;
import com.ku.quizzical.common.util.wrapper.IntegerWrapper;

public class IdHelper {
    /**
     * Creates a mock id
     */
    public static String nextMockId() {
        return MockIdGenerator.newInstance().nextId();
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private IdHelper() {
        super();
    }
}
