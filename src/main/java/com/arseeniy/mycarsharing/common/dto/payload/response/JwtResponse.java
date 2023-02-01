package com.arseeniy.mycarsharing.common.dto.payload.response;

import lombok.Data;

import java.util.List;


@Data
public class JwtResponse {
    private String username;
    private String email;
    private List<String> roles;
    private String token;

    private String type = "Bearer";

    public JwtResponse(String username, String email, List<String> roles, String accessToken) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.token = accessToken;
    }

}
