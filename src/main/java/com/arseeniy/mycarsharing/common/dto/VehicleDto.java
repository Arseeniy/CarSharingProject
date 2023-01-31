package com.arseeniy.mycarsharing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private String stateNumber;

    private String vehicleModel;

    private int fuelAmount;

    private int distanceForVehicle;
}
