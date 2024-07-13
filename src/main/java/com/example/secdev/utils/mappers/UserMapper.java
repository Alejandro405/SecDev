package com.example.secdev.utils.mappers;

import com.example.secdev.model.User;
import com.example.secdev.utils.dtos.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User fromDTO(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public List<UserDTO> toUserDTO(List<User> users) {
        return users.stream().map(this::toDTO).toList();
    }

    public List<User> fromDTO(List<UserDTO> userDTOs) {
        return userDTOs.stream().map(this::fromDTO).toList();
    }
}
