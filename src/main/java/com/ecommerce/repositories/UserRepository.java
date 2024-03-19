package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByIdentifier(String identifier);
}
