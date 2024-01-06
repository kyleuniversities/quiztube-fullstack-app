package com.ku.quizzical.app.controller.user;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.app.helper.AuthorizationValidationHelper;
import com.ku.quizzical.app.helper.DatabaseValidationHelper;
import com.ku.quizzical.app.util.dto.BooleanDto;

/**
 * Controller class for User related operations
 */
@CrossOrigin
@RestController
public final class UserController {
    // Instance Fields
    private UserDatabaseService service;
    private UserRepository repository;

    // Constructor Method
    public UserController(UserDatabaseService service, UserRepository repository) {
        super();
        this.service = service;
        this.repository = repository;
    }

    // CREATE Method
    // Saves a User
    @PostMapping("/users")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserRegistrationRequest user) {
        return new ResponseEntity<UserDto>(this.service.saveUser(user), HttpStatus.OK);
    }

    // READ Method
    // Gets all Users
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return this.service.getAllUsers();
    }

    // READ Method
    // Gets a User by its id
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return new ResponseEntity<UserDto>(this.service.getUser(id), HttpStatus.OK);
    }

    // READ Method
    // Gets a User by its username
    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<UserDto>(this.service.getUserByUsername(username), HttpStatus.OK);
    }

    // READ Method
    // Checks if a User by its username exists
    @GetMapping("/users/exists/{username}")
    public ResponseEntity<BooleanDto> userByIdExists(@PathVariable String username) {
        return new ResponseEntity<BooleanDto>(this.service.userByIdExists(username), HttpStatus.OK);
    }

    // READ Method
    // Checks if a User by its username exists
    @GetMapping("/users/username-exists/{username}")
    public ResponseEntity<BooleanDto> userByUsernameExists(@PathVariable String username) {
        return new ResponseEntity<BooleanDto>(this.service.userByUsernameExists(username),
                HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a User
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id,
            @RequestHeader("Authorization") String authorizationHeader) {
        User matchingUser = DatabaseValidationHelper.validateExistingResourceWithFallthrough("User",
                id, this.repository::findById);
        AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                matchingUser.getId());
        this.service.deleteUser(id);
        return "\"The User with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
