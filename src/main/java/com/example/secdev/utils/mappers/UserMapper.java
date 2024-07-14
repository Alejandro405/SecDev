package com.example.secdev.utils.mappers;

import com.example.secdev.model.User;
import com.example.secdev.utils.dtos.UserDTO;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User fromDTO(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId() == null ? null : userDTO.getId().longValue())
                .name(userDTO.getName())
                .lastName(userDTO.getLastname())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles(RoleMapper.fromDTO(userDTO.getRoles()))
                .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(BigInteger.valueOf(user.getId()))
                .name(user.getName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .roles(RoleMapper.toDTO(user.getRoles()))
                .build();
    }

    public List<UserDTO> toUserDTO(List<User> users) {
        return users.stream().map(this::toDTO).toList();
    }

    public List<User> fromDTO(List<UserDTO> userDTOs) {
        return userDTOs.stream().map(this::fromDTO).toList();
    }
}
