package com.ku.quizzical.app.controller.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.controller.user.UserDtoMapper;
import com.ku.quizzical.app.helper.ApiExceptionHelper;
import com.ku.quizzical.app.helper.JwtHelper;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDtoMapper userDtoMapper;

    public AuthenticationService(AuthenticationManager authenticationManager,
            UserDtoMapper userDtoMapper) {
        this.authenticationManager = authenticationManager;
        this.userDtoMapper = userDtoMapper;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        System.out.println("LOGIN");
        Authentication authentication =
                this.collectAuthentication(request.username(), request.password());
        System.out.println("LOGIN 2");
        User principal = (User) authentication.getPrincipal();
        System.out.println("USERNAME: " + request.username());
        System.out.println("PRINCIPAL: " + principal);
        UserDto userDto = this.userDtoMapper.apply(principal);
        String token = JwtHelper.issueToken(userDto.username(), userDto.id(), userDto.roles());
        return new AuthenticationResponse(token, userDto);
    }

    private Authentication collectAuthentication(String username, String password) {
        try {
            return this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception exception) {
            ApiExceptionHelper.throwResourceNotFoundException("Incorrect username or password.");
            return null;
        }
    }
}
