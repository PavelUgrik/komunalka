package com.example.komunalka.repositories;

import com.example.komunalka.entities.User;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {
    List<User> findAll();
    UserDetails findById(Long id);
    UserDetails findByUsername(String username);
    void save(User user);
}
