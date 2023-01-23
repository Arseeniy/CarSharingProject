package com.arseeniy.mycarsharing.common;

import lombok.Data;

@Data
public class VehicleViewer {

    private String vehicleModel;

    private int vehicleRunningDistance;

    private int distanceForVehicle;

    public VehicleViewer(String vehicleModel, int vehicleRunningDistance, int distanceForVehicle) {
        this.vehicleModel = vehicleModel;
        this.vehicleRunningDistance = vehicleRunningDistance;
        this.distanceForVehicle = distanceForVehicle;
    }
}
