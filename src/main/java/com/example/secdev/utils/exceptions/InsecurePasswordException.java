package com.example.secdev.utils.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password length must be 12 chars minimum!")
public class InsecurePasswordException extends RuntimeException {
    public InsecurePasswordException(String s) {
        super(s);
    }


}
