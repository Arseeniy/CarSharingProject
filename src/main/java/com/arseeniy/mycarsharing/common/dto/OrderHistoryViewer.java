package com.arseeniy.mycarsharing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrderHistoryViewer {

    private String stateNumber;

    private String vehicleModel;

    private LocalDate rentingStart;

    private LocalDate rentingEnd;

}
