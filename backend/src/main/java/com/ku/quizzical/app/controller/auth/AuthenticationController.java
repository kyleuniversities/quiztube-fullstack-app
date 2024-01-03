package com.ku.quizzical.app.controller.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Authentication related operations
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public final class AuthenticationController {
    // Instance Fields
    private final AuthenticationService authenticationService;

    // Constructor Method
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // CREATE Method
    // Logs in a user
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = this.authenticationService.login(request);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, response.token())
                .body(response);
    }
}
