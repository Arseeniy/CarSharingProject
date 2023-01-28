package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.dto.ClientDto;

public interface ClientService {

    public String createClient(ClientDto clientDto);
    public String authorizeClient(String userName, String password);
}
