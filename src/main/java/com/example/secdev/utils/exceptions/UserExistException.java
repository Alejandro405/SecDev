package com.example.secdev.utils.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!")
public class UserExistException extends RuntimeException {
    public UserExistException(String s) {
        super(s);
    }


}
