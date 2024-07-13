package com.example.secdev.controller;


import com.example.secdev.service.UserService;
import com.example.secdev.utils.dtos.PasswordDTO;
import com.example.secdev.utils.dtos.StatusDTO;
import com.example.secdev.utils.dtos.UserDTO;
import com.example.secdev.utils.exceptions.UserEmailNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public UserDTO singupUser(@Valid @RequestBody UserDTO singupRequest) {
        return userService.signup(singupRequest);
    }

    @PostMapping("/changepass")
    public StatusDTO changePassword(@RequestBody @Valid PasswordDTO request) throws UserEmailNotFoundException {
        return userService.changePass(request);
    }


}
