package com.arseeniy.mycarsharing.common.dto;

import lombok.Data;

@Data
public class AvailableVehicleViewer {

    private String stateNumber;

    private String vehicleModel;

    private String vehicleClass;

    private String transmissionType;

    private double dayPrice;

    public AvailableVehicleViewer(String stateNumber, String vehicleModel, String vehicleClass, String transmissionType, double dayPrice) {
        this.stateNumber = stateNumber;
        this.vehicleModel = vehicleModel;
        this.vehicleClass = vehicleClass;
        this.transmissionType = transmissionType;
        this.dayPrice = dayPrice;
    }
}
