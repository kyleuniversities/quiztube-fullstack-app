package com.ku.quizzical.app.controller.user;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ListHelper;

@Service
public class UserServiceImpl implements UserService {
    // Instance Fields
    private UserRepository repository;
    private UserDtoMapper dtoMapper;

    // Constructor Method
    public UserServiceImpl(UserRepository repository) {
        super();
        this.repository = repository;
        this.dtoMapper = new UserDtoMapper();
    }

    // Interface Methods
    @Override
    public UserDto saveUser(UserRegistrationRequest userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
        user.setQuizzes(ListHelper.newArrayList());
        return this.dtoMapper.apply(this.repository.save(user));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return ListHelper.map(this.repository.findAll(), this.dtoMapper::apply);
    }

    @Override
    public UserDto getUserById(String id) {
        return this.dtoMapper.apply(this.repository.findById(id).get());
    }

    @Override
    public UserDto updateUser(UserDto user, String id) {
        User existingUser = this.repository.findById(id).get();
        existingUser.setUsername(user.username());
        existingUser.setEmail(user.email());
        this.repository.save(existingUser);
        return this.dtoMapper.apply(existingUser);
    }

    @Override
    public void deleteUser(String id) {
        this.repository.deleteById(id);
    }
}
