package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> authenticateUser(String username, String password);

    public ResponseEntity<?> registerUser(SignupRequest signupRequest);
}
