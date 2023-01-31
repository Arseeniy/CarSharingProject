package com.arseeniy.mycarsharing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}