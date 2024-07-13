package com.example.secdev.service;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    /**private final UserService userService;
    private final UserRepo userRepo;

    private static final Set<String> insecurePasswords = Set.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserService userService, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public static void verifyPassword(String password) {
        if (password.length() < 12) {
            throw new InsecurePasswordException("Password length must be 12 chars minimum!");
        }

        if (insecurePasswords.contains(password)) {
            throw new BreachedPasswordException("The password is in the hacker's database!");
        }
    }

    /**
     * Dar de alta al usuario, sii no existe otro usuario con el mismo email. En caso de que exista, mensaje tipo info en el logg y devolver null
     * @param singupRequest datos del usuario a dar de alta
     *
    public Optional<User> singupUser(SingupRequest singupRequest) {
        // Pasar el DTO a la entidad User
        return Optional.of(userService.singupUser(singupRequest));
    }

    /**
     * Verify that user passwords contain at least 12 characters
     * Verify that users can change their passwords. Changing the password requires the current and a new password
     * Verify that the passwords submitted during a registration, login, and password change are checked against a set of breached passwords. If the password is breached, the application must require users to set a new non-breached password.
     * Verify that passwords are stored in a form that is resistant to offline attacks. Passwords must be salted and hashed using an approved one-way key derivation or a password hashing function
     * the work factor must be as large as the verification server performance will allow. At least 13.
     *
    public void changePassword(String email, String newPassword) throws UserEmailNotFoundException {
        User user = userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));
        if (Objects.isNull(user)) {
            throw new UserEmailNotFoundException("User not found");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new InsecurePasswordException("The passwords must be different!");
        }

        verifyPassword(newPassword);

        System.out.println("Password encriptado 1: " + user.getPassword());
        userService.changePassword(user.getPassword(), newPassword);
        System.out.println("Password encriptado 2: " + user.getPassword());
    }*/
}
