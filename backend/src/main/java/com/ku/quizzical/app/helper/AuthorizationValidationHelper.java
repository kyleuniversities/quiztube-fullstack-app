package com.ku.quizzical.app.helper;

import com.ku.quizzical.common.helper.ConditionalHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

/**
 * Helper class for Authorization Validation Exception Operations
 */
public class AuthorizationValidationHelper {
        /**
         * Validate if a the user has authorization to perform the action
         */
        public static void validateAuthorization(String authorizationHeader, String targetUserId) {
                String sessionUserId = AuthorizationValidationHelper
                                .validateAuthenticationWithFallthrough(authorizationHeader);
                ConditionalHelper.ifThen(!sessionUserId.equals(targetUserId),
                                () -> AuthorizationValidationHelper.throwBadCredentialsException());
        }

        /**
         * Throw bad credentials exception
         */
        private static void throwBadCredentialsException() {
                ApiExceptionHelper.throwBadCredentialsException(
                                "You are not authorized to perform the operation.");
        }

        /**
         * Throw insufficient authentication exception
         */
        private static void throwInsufficientAuthenticationException() {
                ApiExceptionHelper.throwInsufficientAuthenticationException(
                                "You must be logged in to perform the operation.");
        }

        /**
         * Validate if a user is authenticated through an Authorization Header by a JWT Token, and
         * returns the user id if successful
         */
        private static String validateAuthenticationWithFallthrough(String authorizationHeader) {
                if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                        AuthorizationValidationHelper.throwInsufficientAuthenticationException();
                }
                try {
                        String token = authorizationHeader.substring(7);
                        Claims claims = JwtHelper.getClaims(token);
                        if (!claims.containsKey("id")) {
                                AuthorizationValidationHelper
                                                .throwInsufficientAuthenticationException();
                        }
                        Object idObject = claims.get("id");
                        if (idObject == null || !(idObject instanceof String)) {
                                AuthorizationValidationHelper
                                                .throwInsufficientAuthenticationException();
                        }
                        return (String) idObject;
                } catch (MalformedJwtException e) {
                        AuthorizationValidationHelper.throwInsufficientAuthenticationException();
                } catch (ExpiredJwtException e) {
                        AuthorizationValidationHelper.throwInsufficientAuthenticationException();
                }
                return null;
        }

        /**
         * Private Constructor to prevent instantiation
         */
        private AuthorizationValidationHelper() {
                super();
        }
}
