package com.example.secdev.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    @JsonProperty("new_password")
    @NotBlank(message = "NewPassword is mandatory")
    private String new_password;

    public String getNew_password() {
        return this.new_password;
    }
}
