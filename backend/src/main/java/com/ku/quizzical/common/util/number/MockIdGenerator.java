package com.ku.quizzical.common.util.number;

import java.util.List;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.RandomHelper;
import com.ku.quizzical.common.helper.TimeHelper;
import com.ku.quizzical.common.helper.list.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

public class MockIdGenerator {
    // Instance Fields
    private StringBuilder idTextBuilder;
    private String timeText;
    private int textIndex;
    private List<Boolean> textPlacementConfigurationList;

    // New Instance Method
    public static MockIdGenerator newInstance() {
        return new MockIdGenerator();
    }

    // Constructor Method
    public MockIdGenerator() {
        super();
    }

    // Main Instance Method
    public String nextId() {
        this.reset();
        ListHelper.forEach(this.textPlacementConfigurationList,
                (Integer i, Boolean isUsingText) -> {
                    ConditionalHelper.ifThen(i == 8 || i == 12,
                            () -> this.idTextBuilder.append("-"));
                    ConditionalHelper.ifThen(isUsingText, () -> this.idTextBuilder
                            .append(this.timeText.charAt(this.textIndex++)));
                    ConditionalHelper.ifThen(!isUsingText,
                            () -> this.idTextBuilder.append(this.nextHexidecimalLetter()));
                });
        return this.idTextBuilder.toString();
    }

    // Major Methods
    private char nextHexidecimalLetter() {
        return (char) ('a' + RandomHelper.nextInt(6));
    }

    // Initialization Methods
    private String makeTimeText() {
        return StringHelper.newBuilder().append(TimeHelper.getInstantEpochMillisecondsInPst() + "")
                .reverse().toString();
    }

    private List<Boolean> makeTextPlacementConfigurationList() {
        List<Boolean> textPlacementConfigurationList = ListHelper.newArrayList();
        IterationHelper.forEach(12, () -> textPlacementConfigurationList.add(true));
        IterationHelper.forEach(8, () -> textPlacementConfigurationList.add(false));
        ListHelper.shuffle(textPlacementConfigurationList);
        return textPlacementConfigurationList;
    }

    private void reset() {
        this.idTextBuilder = StringHelper.newBuilder();
        this.timeText = this.makeTimeText();
        this.textIndex = 0;
        this.textPlacementConfigurationList = this.makeTextPlacementConfigurationList();
    }
}
