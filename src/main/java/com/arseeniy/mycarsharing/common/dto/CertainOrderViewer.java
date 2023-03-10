package com.arseeniy.mycarsharing.common.dto;

import com.arseeniy.mycarsharing.common.entity.booking.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CertainOrderViewer {

    private String username;

    private String stateNumber;

    private String vehicleModel;

    private OrderStatus orderStatus;

    private Double dayPrice;

    private Double rentPrice;

    private Double damagePrice;

    private String damageDescription;

    private LocalDate rentingStart;

    private LocalDate rentingEnd;
}
