package com.ku.quizzical.app.controller.user;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ListHelper;

@Service
public class UserOrdinaryService {
    // Instance Fields
    private UserRepository repository;
    private UserDtoMapper dtoMapper;

    // Constructor Method
    public UserOrdinaryService(UserRepository repository) {
        super();
        this.repository = repository;
        this.dtoMapper = new UserDtoMapper();
    }

    // Interface Methods
    public UserDto saveUser(UserRegistrationRequest userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
        user.setQuizzes(ListHelper.newArrayList());
        return this.dtoMapper.apply(this.repository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return ListHelper.map(this.repository.findAll(), this.dtoMapper::apply);
    }

    public UserDto getUserById(String id) {
        return this.dtoMapper.apply(this.repository.findById(id).get());
    }

    public UserDto updateUser(UserDto user, String id) {
        User existingUser = this.repository.findById(id).get();
        existingUser.setUsername(user.username());
        existingUser.setEmail(user.email());
        this.repository.save(existingUser);
        return this.dtoMapper.apply(existingUser);
    }

    public void deleteUser(String id) {
        this.repository.deleteById(id);
    }
}
