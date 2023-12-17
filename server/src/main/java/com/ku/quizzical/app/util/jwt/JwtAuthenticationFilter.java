package com.ku.quizzical.app.util.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ku.quizzical.app.helper.JwtHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for filtering with Jwt authentication
 */
@Component
public final class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Instance Fields
    private UserDetailsService userDetailsService;

    // New Instance Method
    public static JwtAuthenticationFilter newInstance(UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(userDetailsService);
    }

    // Constructor Method
    private JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        super();
        this.userDetailsService = userDetailsService;
    }

    // Accessor Methods
    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    // Main Instance Method
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // Collects authorization header
        String authorizationHeader = request.getHeader("Authorization");

        // If there is no valid JWT token skip to performing the filter
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token data
        String token = authorizationHeader.substring(7);
        String subject = JwtHelper.getSubject(token);

        // Set the security context to the subject if the context
        // is empty and the token is valid
        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            if (JwtHelper.isValidToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Filter perform filter chain operation
        filterChain.doFilter(request, response);
    }
}
