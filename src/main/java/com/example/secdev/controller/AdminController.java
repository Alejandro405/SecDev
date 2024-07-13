package com.example.secdev.controller;


import com.example.secdev.service.UserService;
import com.example.secdev.utils.ErrorResponse;
import com.example.secdev.utils.UpdateUserInformation;
import com.example.secdev.utils.dtos.UserDTO;
import com.example.secdev.utils.exceptions.AdminOperationNotSupported;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserInformation userInformation) {
        UserDTO res = new UserDTO();

        if (userInformation.getRole().isEmpty())
            return ResponseEntity.ok().build();

        switch (userInformation.getOperation()) {
            case "GRANT":
                res = userService.grantRole(userInformation);
                break;
            case "REMOVE":
                res = userService.removeRole(userInformation);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUser(
            @PathVariable
            @NotBlank
            @Pattern(
                    regexp = ".*@acme\\.com$",
                    message = "Email must be a corporate email ending with @acme.com") String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

}
