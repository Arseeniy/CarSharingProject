package com.arseeniy.mycarsharing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestForVehicleCreation {

    private String stateNumber;

    private String vehicleModel;

    private String vehicleClass;

    private int doorsCount;

    private int passengersCount;

    private boolean isAirConditioning;

    private boolean isAudio;

    private String fuelType;

    private int fuelTankCapacity;

    private int enginePower;

    private String transmissionType;

    private boolean isAvailable;

}
