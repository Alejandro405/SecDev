package com.example.secdev.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User email not found")
public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
