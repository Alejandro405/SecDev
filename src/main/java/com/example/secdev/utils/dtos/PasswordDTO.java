package com.example.secdev.utils.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordDTO {
    @NotBlank
    @Size(min = 12, max = 50)
    private String new_password;

    public String getNew_password() {
        return new_password;
    }
}
