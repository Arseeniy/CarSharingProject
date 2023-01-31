package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.payload.request.LoginRequest;
import com.arseeniy.mycarsharing.common.dto.payload.request.SignupRequest;
import com.arseeniy.mycarsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }

}
