package com.example.secdev.utils;

public class ChangePasswordResponse {
    private String email;

    private String status = "The password has been updated successfully";

    public ChangePasswordResponse() {
    }

    public ChangePasswordResponse(String email, String status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}
