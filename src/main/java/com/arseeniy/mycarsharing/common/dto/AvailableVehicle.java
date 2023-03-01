package com.arseeniy.mycarsharing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableVehicle {

    private String stateNumber;

    private String vehicleModel;

    private String vehicleClass;

    private String transmissionType;

    private double dayPrice;

}
