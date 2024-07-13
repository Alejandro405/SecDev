package com.example.secdev.utils;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SingupResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastname")  // Use "lastname" consistently
    private String lastName;

    @JsonProperty("email")
    private String email;

    public SingupResponse() {
    }

    public SingupResponse(Long id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public SingupResponse(String name, String lastName, String email) {
        // Si alguno de los argumentos es cadena vacía excepción
        if (name == null || lastName == null || email == null) {
            throw new IllegalArgumentException("None of the arguments can be null");
        }

        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
