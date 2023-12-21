package com.example.groceries.security.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.groceries.security.entity.Role;
import com.example.groceries.security.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findUserByUsername(String username);
    User findByRole(Role role);
}
