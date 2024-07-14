package com.example.secdev.repo;


import com.example.secdev.model.User;
import com.example.secdev.utils.dtos.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String s);
}
