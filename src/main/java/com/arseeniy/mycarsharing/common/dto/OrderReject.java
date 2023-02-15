package com.arseeniy.mycarsharing.common.dto;

import lombok.Data;

@Data
public class OrderReject {

    private String rejectDescription;

    private String stateNumber;

    private String username;
}
