package com.ft.flight.service;

import com.ft.flight.entity.User;
import com.ft.flight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public User registerUser(User user) {
        // Check if the username or email is already taken
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Username or email is already taken");
        }

        // Encrypt the password before saving it to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        return userRepository.save(user);
    }

    public User loginUser(User user) {
        // Find the user by username
        User storedUser = userRepository.findByUsername(user.getUsername());

        // Check if the user exists
        if (storedUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + user.getUsername());
        }

        // Check if the provided password matches the stored password
        if (!passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Login successful
        return storedUser;
    }

    // Find a user by username
    public User findUserByUsername(String username) {
        return (User) userRepository.findByUsername(username);
    }
}

