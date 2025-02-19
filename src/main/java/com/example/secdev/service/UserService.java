package com.example.secdev.service;


import com.example.secdev.config.CurrentUser;
import com.example.secdev.config.PasswordBlacklist;
import com.example.secdev.model.Role;
import com.example.secdev.model.User;
import com.example.secdev.repo.RolesRepo;
import com.example.secdev.repo.UserRepo;
import com.example.secdev.utils.UpdateUserInformation;
import com.example.secdev.utils.dtos.PasswordDTO;
import com.example.secdev.utils.dtos.StatusDTO;
import com.example.secdev.utils.dtos.UserDTO;
import com.example.secdev.utils.mappers.RoleMapper;
import com.example.secdev.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    private final RolesRepo rolesRepo;
    private final RoleMapper roleMapper;

    private UserRepo userRepo;
    private UserMapper userMapper;
    private CurrentUser currentUser;

    private PasswordEncoder passwordEncoder;
    private PasswordBlacklist passwordBlacklist;

    public UserDTO signup(UserDTO userDto) {
        throwIfUserExistsByEmailIgnoreCase(userDto.getEmail());
        throwIfPasswordInBlackList(userDto.getPassword());

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setEmail(userDto.getEmail().toLowerCase());

        User user = userRepo.save(userMapper.fromDTO(userDto));
        return userMapper.toDTO(user);
    }

    public StatusDTO changePass(PasswordDTO passDto) {
        User currUser = currentUser.getCurrentUser();

        throwIfPasswordsMatch(passDto.getNew_password(), currUser.getPassword());
        throwIfPasswordInBlackList(passDto.getNew_password());

        currUser.setPassword(passwordEncoder.encode(passDto.getNew_password()));
        userRepo.save(currUser);

        return new StatusDTO(currUser.getEmail(), "The password has been updated successfully");
    }

    // Exceptions

    private void throwIfPasswordsMatch(String rawPassword, String encodedPass) {
        if (passwordEncoder.matches(rawPassword, encodedPass)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password must be different!");
        }
    }

    private void throwIfUserExistsByEmailIgnoreCase(String email) {
        if (userRepo.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
    }

    private void throwIfPasswordInBlackList(String password) {
        if (passwordBlacklist.isPasswordInBlacklist(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByEmailIgnoreCase(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    public UserDTO grantRole(UpdateUserInformation userInformation) {
        String userName = userInformation.getUser();
        String newRole = userInformation.getRole();
        return grantRole(userName, newRole);
    }

    private UserDTO grantRole(String userName, String newRole) {
        User user = userRepo.findByEmailIgnoreCase(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Role role = rolesRepo.findByName(newRole)
                .orElse(new Role(null, "New role created, add description", Set.of(user)));
        user.getRoles().add(role);
        userRepo.save(user);

        return userMapper.toDTO(user);
    }

    public UserDTO revokeRole(UpdateUserInformation userInformation) {
        String userName = userInformation.getUser();
        String role = userInformation.getRole();

        return revokeRole(userName, role);
    }

    private UserDTO revokeRole(String userName, String roleToRevoke) {
        User user  = userRepo.findByEmailIgnoreCase(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Role role = rolesRepo.findByName(roleToRevoke)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        user.getRoles().remove(role);
        userRepo.save(user);

        return userMapper.toDTO(user);
    }

    public void deleteUser(String email) {
        User user = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepo.delete(user);
    }

    public List<UserDTO> getUsers() {


        return userRepo.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
