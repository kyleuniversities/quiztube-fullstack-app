package com.ku.quizzical.app.helper;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import com.ku.quizzical.app.exception.DuplicateResourceException;
import com.ku.quizzical.app.exception.RequestValidationException;
import com.ku.quizzical.app.exception.ResourceNotFoundException;

/**
 * Helper class for Api Exception Operations
 */
public class ApiExceptionHelper {
    /**
     * Throw Bad Credentials Exception
     */
    public static void throwBadCredentialsException(String message) {
        throw new BadCredentialsException(message);
    }

    /**
     * Throw Duplicate Resource Exception
     */
    public static void throwDuplicateResourceException(String message) {
        throw new DuplicateResourceException(message);
    }

    /**
     * Throw Insufficient Authentication Exception
     */
    public static void throwInsufficientAuthenticationException(String message) {
        throw new InsufficientAuthenticationException(message);
    }

    /**
     * Throw Request Validation Exception
     */
    public static void throwRequestValidationException(String message) {
        throw new RequestValidationException(message);
    }

    /**
     * Throw Resource Not Found Exception
     */
    public static void throwResourceNotFoundException(String message) {
        throw new ResourceNotFoundException(message);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ApiExceptionHelper() {
        super();
    }
}
