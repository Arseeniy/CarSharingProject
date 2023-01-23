package com.arseeniy.mycarsharing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchElementFoundException extends RuntimeException {

    public NoSuchElementFoundException(String userName) {
        super("Client with user name " + userName + " does not exist");
    }
}
