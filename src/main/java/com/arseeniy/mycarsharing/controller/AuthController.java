package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.payload.request.LoginRequest;
import com.arseeniy.mycarsharing.common.dto.payload.request.SignupRequest;
import com.arseeniy.mycarsharing.common.dto.payload.response.JwtResponse;
import com.arseeniy.mycarsharing.common.dto.payload.response.MessageResponse;
import com.arseeniy.mycarsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(userService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return ResponseEntity.ok(userService.registerUser(signUpRequest));
    }

}
