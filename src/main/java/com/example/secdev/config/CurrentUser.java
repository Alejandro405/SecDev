package com.example.secdev.config;


import com.example.secdev.model.User;
import com.example.secdev.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CurrentUser {
    private final UserRepo userRepo;

    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        return userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
