package com.ku.quizzical.app.helper;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Helper class for Backend Validation Exception Operations
 */
public class BackendValidationHelper {
    /**
     * Validate if a resource is exists
     */
    public static <T> T validateExistingResourceWithFallthrough(String attributeName,
            String queryToken, Function<String, Optional<T>> existingResourceCollector) {
        try {
            return BackendValidationHelper.validateExistingResourceWithFallthrough(attributeName,
                    existingResourceCollector.apply(queryToken)).get();
        } catch (Exception exception) {
            ApiExceptionHelper.throwResourceNotFoundException(
                    StringHelper.format("%s does not exist.", attributeName));
        }
        return null;
    }

    /**
     * Validate if a resource is exists
     */
    public static <T> T validateExistingResourceWithFallthrough(String attributeName,
            T existingResource) {
        ConditionalHelper.ifThen(existingResource == null,
                () -> ApiExceptionHelper.throwResourceNotFoundException(
                        StringHelper.format("%s does not exist.", attributeName)));
        return existingResource;
    }

    /**
     * Validate if a resource is unique
     */
    public static <T> void validateUniqueResource(String attributeName,
            Supplier<T> existingResourceSupplier) {
        ConditionalHelper.ifThen(existingResourceSupplier.get() != null,
                () -> ApiExceptionHelper.throwDuplicateResourceException(
                        StringHelper.format("%s already exists.", attributeName)));
    }

    /**
     * Validate if a text resource is unique
     */
    public static <T> void validateUniqueTextResource(String attributeName, String text,
            Function<String, T> existingResourceCollector) {
        BackendValidationHelper.validateUniqueResource(attributeName,
                () -> existingResourceCollector.apply(text));
    }

    /**
     * Validate if a text resource is unique if entered
     */
    public static <T> void validateUniqueTextResourceIfExists(String attributeName,
            Supplier<String> textSupplier, Function<String, T> existingResourceCollector) {
        String text = textSupplier.get();
        ConditionalHelper.ifThen(text != null, () -> BackendValidationHelper
                .validateUniqueTextResource(attributeName, text, existingResourceCollector));
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private BackendValidationHelper() {
        super();
    }
}
