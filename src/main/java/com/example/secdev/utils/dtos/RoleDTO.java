package com.example.secdev.utils.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoleDTO {
    @NotBlank
    private String name;
    private String description;
}
