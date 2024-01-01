package com.ku.quizzical.app.helper;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.string.CharacterHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Helper class for Text Validation Exception Operations
 */
public class TextValidationHelper {
    /**
     * Check text to be in email format
     */
    public static boolean isPassableEmail(String text) {
        List<Integer> atList = StringHelper.listIndices(text, '@');
        int lastDotIndex = StringHelper.lastIndexOf(text, '.');
        return atList.size() == 1 && 0 < atList.get(0) && atList.get(0) < lastDotIndex
                && lastDotIndex < text.length() - 1;
    }

    /**
     * Validate first character of text to be alphabetical
     */
    public static void validateFirstCharacterToBeAlphabetical(String attributeName, String text) {
        ConditionalHelper.ifThen(!CharacterHelper.isAlphabetical(text.charAt(0)),
                () -> ApiExceptionHelper.throwRequestValidationException(StringHelper.format(
                        "%s must have its first character to be alphabetical.", attributeName)));
    }

    /**
     * Validate text if it exists
     */
    public static void validateIfExists(Supplier<String> textSupplier, Consumer<String> validator) {
        TextValidationHelper.validateIfExists(textSupplier.get(), validator);
    }

    /**
     * Validate text if it exists
     */
    public static void validateIfExists(String text, Consumer<String> validator) {
        ConditionalHelper.ifThen(text != null, () -> validator.accept(text));
    }

    /**
     * Validate text to be non null
     */
    public static void validateNonNull(String attributeName, String text) {
        ConditionalHelper.ifThen(text == null,
                () -> ApiExceptionHelper.throwRequestValidationException(
                        StringHelper.format("%s cannot be null.", attributeName)));
    }

    /**
     * Validate text to be alphabetical
     */
    public static void validateToBeAlphabetical(String attributeName, String text) {
        ConditionalHelper.ifThen(!StringHelper.isAlphabetical(text),
                () -> ApiExceptionHelper.throwRequestValidationException(
                        StringHelper.format("%s must be alphabetical.", attributeName)));
    }

    /**
     * Validate text to be alphanumeric
     */
    public static void validateToBeAlphanumeric(String attributeName, String text) {
        ConditionalHelper.ifThen(!StringHelper.isAlphanumeric(text),
                () -> ApiExceptionHelper.throwRequestValidationException(
                        StringHelper.format("%s must be alphanumeric.", attributeName)));
    }

    /**
     * Validate text length to be in passable email format
     */
    public static void validateEmail(String text) {
        ConditionalHelper.ifThen(!TextValidationHelper.isPassableEmail(text),
                () -> ApiExceptionHelper.throwRequestValidationException("Email must be valid."));
    }

    /**
     * Validate text length to be within specified range
     */
    public static void validateLength(String attributeName, String text, int min, int max) {
        ConditionalHelper
                .ifThen(text.length() < min || text.length() > max,
                        () -> ApiExceptionHelper.throwRequestValidationException(
                                StringHelper.format("%s must be between %d and %d characters.",
                                        attributeName, min, max)));
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private TextValidationHelper() {
        super();
    }
}
