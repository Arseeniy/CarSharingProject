package com.arseeniy.mycarsharing.controllers;

import com.arseeniy.mycarsharing.dto.ClientDto;

import com.arseeniy.mycarsharing.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class GreetingController {

    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping("/registration")
    public String clientRegistration(@RequestBody ClientDto clientDto) {
        return clientService.createClient(clientDto);
    }

    @GetMapping("/auth/{userName}/{password}")
    public String clientAuthorization(@RequestParam(name = "userName", required = true) String userName,
                                      @RequestParam(name = "password", required = true) String password) {
        return clientService.authorizeClient(userName, password);
    }
}
