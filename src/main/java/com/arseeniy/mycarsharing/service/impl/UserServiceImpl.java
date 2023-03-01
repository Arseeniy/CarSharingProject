package com.arseeniy.mycarsharing.service.impl;

import com.arseeniy.mycarsharing.common.dto.payload.request.SignupRequest;
import com.arseeniy.mycarsharing.common.dto.payload.response.JwtResponse;
import com.arseeniy.mycarsharing.common.dto.payload.response.MessageResponse;
import com.arseeniy.mycarsharing.common.entity.authorization.ERole;
import com.arseeniy.mycarsharing.common.entity.authorization.Role;
import com.arseeniy.mycarsharing.common.entity.authorization.User;
import com.arseeniy.mycarsharing.exception.CustomException;
import com.arseeniy.mycarsharing.repository.RoleRepository;
import com.arseeniy.mycarsharing.repository.UserRepository;
import com.arseeniy.mycarsharing.security.JwtUtils;
import com.arseeniy.mycarsharing.security.service.UserDetailsImpl;
import com.arseeniy.mycarsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    public JwtResponse authenticateUser(String username, String password) {

        if (!userRepository.existsByUsername(username)) {
            throw new CustomException("User does not exist! Please, try once more or create account");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                jwt);
    }

    @Override
    public MessageResponse registerUser(SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {

            throw new CustomException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {

            throw new CustomException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepository.findByName(ERole.ROLE_USER).
//                orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
//
//        user.setRoles(roles);

        user.setRole(roleRepository.findByName(ERole.ROLE_USER).
                orElseThrow(() -> new RuntimeException("Error: Role is not found.")));

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }
}
