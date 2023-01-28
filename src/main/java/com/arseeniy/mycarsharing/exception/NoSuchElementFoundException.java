package com.arseeniy.mycarsharing.exception;

public class NoSuchElementFoundException extends RuntimeException {

    public NoSuchElementFoundException(String userName) {
        super("Client with user name " + userName + " does not exist");
    }
}
