package com.arseeniy.mycarsharing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingVehicleViewer {

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

    private double dayPrice;
}
