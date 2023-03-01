package com.arseeniy.mycarsharing.common.dto.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;


@Data
@AllArgsConstructor
public class JwtResponse {

    private String username;

    private String email;

    private List<String> roles;

    private String token;

}
