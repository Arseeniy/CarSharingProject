package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.payload.request.SignupRequest;
import com.arseeniy.mycarsharing.common.dto.payload.response.JwtResponse;
import com.arseeniy.mycarsharing.common.dto.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public JwtResponse authenticateUser(String username, String password);

    public MessageResponse registerUser(SignupRequest signupRequest);
}
