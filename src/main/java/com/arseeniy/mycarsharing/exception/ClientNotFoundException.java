package com.arseeniy.mycarsharing.exception;


public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String userName) {
        super("Client with user name " + userName + " does not exist");
    }
}
