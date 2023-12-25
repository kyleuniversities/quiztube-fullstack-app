package com.ku.quizzical.app.helper;

import static java.time.temporal.ChronoUnit.DAYS;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.ku.quizzical.common.helper.MapHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Helper class for Jwt Operations
 */
public class JwtHelper {
    /**
     * Constant for secret key
     */
    private static final String SECRET_KEY = "sample_key_123456789012345678901234567890";

    /**
     * Returns the claims of a JWT token
     */
    public static Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(JwtHelper.getJwtSigningKey()).build()
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * Returns the JWT signing key
     */
    public static Key getJwtSigningKey() {
        return Keys.hmacShaKeyFor(JwtHelper.SECRET_KEY.getBytes());
    }

    /**
     * Returns the subject of a JWT token
     */
    public static String getSubject(String token) {
        return JwtHelper.getClaims(token).getSubject();
    }

    /**
     * Checks if a token is expired
     */
    public static boolean isExpiredToken(String token) {
        Date now = Date.from(Instant.now());
        return JwtHelper.getClaims(token).getExpiration().before(now);
    }

    /**
     * Issues a JWT Token
     */
    public static String issueToken(String subject) {
        return JwtHelper.issueToken(subject, Map.of());
    }

    /**
     * Issues a JWT Token
     */
    public static String issueToken(String subject, String... scopes) {
        return JwtHelper.issueToken(subject, Map.of("scopes", scopes));
    }

    /**
     * Issues a JWT Token
     */
    public static String issueToken(String subject, String id, List<String> scopes) {
        Map<String, Object> claims = MapHelper.newLinkedHashMap();
        MapHelper.put(claims, "id", id);
        MapHelper.put(claims, "scopes", scopes);
        return JwtHelper.issueToken(subject, claims);
    }

    /**
     * Issues a JWT Token
     */
    public static String issueToken(String subject, Map<String, Object> claims) {
        String token = Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuer("quizzical_placeholder_issuer").setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, DAYS)))
                .signWith(JwtHelper.getJwtSigningKey()).compact();
        return token;
    }

    /**
     * Checks if a token is valid
     */
    public static boolean isValidToken(String token, String username) {
        String subject = JwtHelper.getSubject(token);
        return subject.equals(username) && !JwtHelper.isExpiredToken(token);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private JwtHelper() {
        super();
    }
}
