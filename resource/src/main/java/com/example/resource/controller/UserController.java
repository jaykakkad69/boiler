package com.example.resource.controller;

import com.example.resource.entity.User;
import com.example.resource.exception.ResourceNotFoundException;
import com.example.resource.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long UserId)
        throws ResourceNotFoundException {
        User User = userRepository.findById(UserId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));
        return ResponseEntity.ok().body(User);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User User) {
        return userRepository.save(User);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long UserId,
                                                   @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User User = userRepository.findById(UserId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));

        User.setEmailId(userDetails.getEmailId());
        User.setLastName(userDetails.getLastName());
        User.setFirstName(userDetails.getFirstName());
        final User updatedUser = userRepository.save(User);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long UserId)
        throws ResourceNotFoundException {
        User User = userRepository.findById(UserId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));

        userRepository.delete(User);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}