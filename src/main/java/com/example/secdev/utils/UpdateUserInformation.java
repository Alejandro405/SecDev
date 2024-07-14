package com.example.secdev.utils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserInformation {
    @NotEmpty
    @Email
    @Pattern(regexp = ".*@acme\\.com$", message = "Email must be a corporate email ending with @acme.com")
    private String user;

    private String role;

    private String operation;
}
