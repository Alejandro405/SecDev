package com.example.secdev.utils.mappers;

import com.example.secdev.model.Role;
import com.example.secdev.utils.dtos.RoleDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    public static Role fromDTO(RoleDTO roleDTO) {
        return Role.builder()
                .name(roleDTO.getName())
                .description(roleDTO.getDescription())
                .build();
    }

    public static RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    public static Set<RoleDTO> toDTO(Collection<Role> roles) {
        return roles.stream().map(RoleMapper::toDTO).collect(Collectors.toSet());
    }

    public static Set<Role> fromDTO(Collection<RoleDTO> roleDTOs) {
        return roleDTOs.stream().map(RoleMapper::fromDTO).collect(Collectors.toSet());
    }
}
