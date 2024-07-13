package com.example.secdev.utils;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserInformation {
    @NotEmpty
    private String user;

    private String role;

    private String operation;
}
