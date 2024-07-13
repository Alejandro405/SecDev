package com.example.secdev.utils;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class SingupRequest {
    @JsonProperty("name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @JsonProperty("lastname")
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @JsonProperty("email")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Pattern(regexp = ".*@acme\\.com$", message = "Email must be a corporate email ending with @acme.com")
    private String email;

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is mandatory")
    private String password;

    public SingupRequest() {
    }

    public SingupRequest(String name, String lastName, String email, String password) {
        if (isBlank(name) || isBlank(lastName) || isBlank(email) || isBlank(password)) {
            throw new IllegalArgumentException("None of the arguments can be empty or blank");
        }

        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public boolean validate() {
        return !(isBlank(name) || isBlank(lastname) || isBlank(email) || isBlank(password)) && (email.contains("@acme.com"));
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}