package com.ku.quizzical.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {
    /**
     * Handles exception with insufficient credentials, such as a weak level of authentication
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> handleException(InsufficientAuthenticationException exception,
            HttpServletRequest request) {
        return DefaultExceptionHandler.handleException(exception, request, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles exception with bad credentials, such as invalid credentials
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleException(BadCredentialsException exception,
            HttpServletRequest request) {
        return DefaultExceptionHandler.handleException(exception, request, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles exceptions with requests that did not pass one or more validations
     */
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ApiError> handleException(RequestValidationException exception,
            HttpServletRequest request) {
        return DefaultExceptionHandler.handleException(exception, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions with requestS that required a resource not found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ResourceNotFoundException exception,
            HttpServletRequest request) {
        return DefaultExceptionHandler.handleException(exception, request, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions with requestS that would have created a duplicate resource, like two users
     * with the same username
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleException(DuplicateResourceException exception,
            HttpServletRequest request) {
        return DefaultExceptionHandler.handleException(exception, request, HttpStatus.CONFLICT);
    }

    /**
     * Handles a general server exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception,
            HttpServletRequest request) {
        return DefaultExceptionHandler.handleException(exception, request,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Helper method for general exception handling
     */
    private static ResponseEntity<ApiError> handleException(Exception exception,
            HttpServletRequest request, HttpStatus status) {
        return new ResponseEntity<ApiError>(ApiError.newInstance(request.getRequestURI(),
                exception.getMessage(), status.value(), LocalDateTime.now()), status);
    }

    /**
     * Helper method for general exception handling
     */
    /*
     * private static ResponseEntity<String> handleExceptionAsString(Exception exception,
     * HttpServletRequest request, HttpStatus status) { return new ResponseEntity<String>(
     * StringCodeHelper.toCode(exception.getMessage()), status); }/
     ***/
}
