package com.ecommerce.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.models.User;
import com.ecommerce.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService{

    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Load user details from your data source (e.g., database) based on GitHub user ID
        Optional<User> userOp = userRepository.findByIdentifier(identifier);
        if (!userOp.isPresent()) {
            throw new UsernameNotFoundException("User not found with GitHub user ID: " + identifier);
        }

        User user = userOp.get();
        // Construct UserDetails object with user roles
        return org.springframework.security.core.userdetails.User
                .withUsername(String.valueOf(user.getIdentifier())) // Use GitHub user ID as username
                .password(user.getPassword())
                .authorities(user.getAuthorities()) // Assuming roles are stored as GrantedAuthorities
                .accountExpired(true)
                .accountLocked(true)
                .credentialsExpired(true)
                .disabled(true)
                .build();
    }
}
