package com.example.secdev.utils.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigInteger id;
    @NotBlank
    @Size(max = 30)
    private String name;
    @NotBlank
    @Size(max = 30)
    private String lastname;
    @NotBlank
    @Email(regexp = "\\w{1,51}@acme\\.com$", message = "email must end with '@acme.com' and the size must be between 10 and 60")
    private String email;
    @NotBlank
    @Size(min = 12, max = 50)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
