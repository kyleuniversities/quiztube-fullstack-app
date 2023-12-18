package com.ku.quizzical.common.util.string;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Utility class for replacing substrings within a String
 */
public final class StringReplacer {
    // Instance Fields
    private String text;
    private int index;
    private StringMap replacementMap;
    private StringBuilder replaced;

    // New Instance Method
    public static StringReplacer newInstance() {
        return new StringReplacer();
    }

    // Constructor Method
    private StringReplacer() {
        super();
    }

    // Main Instance Method
    public String replace(String text, StringMap replacementMap) {
        this.reset(text, replacementMap);
        this.forEachCharacter((Character ch) -> {
            boolean noReplacementsFound =
                    this.forEachReplacementEntry((String target, String replacement) -> {
                        if (StringHelper.substringEquals(this.text, target, this.index)) {
                            this.replaced.append(replacement);
                            this.index += target.length() - 1;
                            return false;
                        }
                        return true;
                    });
            ConditionalHelper.ifThen(noReplacementsFound, () -> this.replaced.append(ch));
        });
        return this.replaced.toString();
    }

    // Iteration Methods
    private void forEachCharacter(Consumer<Character> action) {
        while (this.index < this.text.length()) {
            action.accept(this.text.charAt(this.index));
            this.index++;
        }
    }

    private boolean forEachReplacementEntry(BiPredicate<String, String> action) {
        return MapHelper.forEach(this.replacementMap, action);
    }

    // Initialization Methods
    private void reset(String text, StringMap replacementMap) {
        this.text = text;
        this.index = 0;
        this.replacementMap = replacementMap;
        this.replaced = StringHelper.newBuilder();
    }
}
