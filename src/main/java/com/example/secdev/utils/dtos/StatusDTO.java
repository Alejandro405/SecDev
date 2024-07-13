package com.example.secdev.utils.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String status;
}
