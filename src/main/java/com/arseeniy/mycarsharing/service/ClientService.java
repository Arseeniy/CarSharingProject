package com.arseeniy.mycarsharing.service;

public interface ClientService {

    public String createClient(String firstName, String lastName,
                               String userName, String password);

    public String authorizeClient(String userName, String password);
}
