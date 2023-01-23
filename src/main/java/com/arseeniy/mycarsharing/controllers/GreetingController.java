package com.arseeniy.mycarsharing.controllers;

import com.arseeniy.mycarsharing.repository.ClientRepository;
import com.arseeniy.mycarsharing.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class GreetingController {

    @Autowired
    ClientServiceImpl clientService;

    @PostMapping("/registration/{firstName}/{lastName}/{userName}/{password}")
    public String clientRegistration(@PathVariable String firstName, @PathVariable String lastName,
                                     @PathVariable String userName, @PathVariable String password) {
        return clientService.createClient(firstName, lastName, userName, password);
    }

    @GetMapping("/auth/{userName}/{password}")
    public String clientAuthorization(@RequestParam(name = "userName", required = true) String userName,
                                      @RequestParam(name = "password", required = true) String password) {
        return clientService.authorizeClient(userName, password);
    }
}
