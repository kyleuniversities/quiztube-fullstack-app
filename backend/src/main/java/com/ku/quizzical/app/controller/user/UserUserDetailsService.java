package com.ku.quizzical.app.controller.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ConditionalHelper;

@Service
public class UserUserDetailsService implements UserDetailsService {
    private final UserDatabaseService userDatabaseService;
    private UserRepository userRepository;

    public UserUserDetailsService(UserDatabaseService userDatabaseService,
            UserRepository userRepository) {
        this.userDatabaseService = userDatabaseService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = this.userDatabaseService.getUserByUsername(username);
        String userId = ConditionalHelper.newTernaryOperation(userDto == null, () -> "<null>",
                () -> userDto.id());
        return this.userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " not found"));
    }
}
