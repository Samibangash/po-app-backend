package com.poapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poapp.model.User;
import com.poapp.repository.UserRepository;
import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Method to get users by their role
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
    public User registerUser(User user) {
        // Encrypt the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
    
    
}
