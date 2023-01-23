package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.VehicleViewer;
import com.arseeniy.mycarsharing.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    public List<VehicleViewer> getAvailableVehicleList(List<Vehicle> vehicleList);

    public int getVehicleRunningDistance(int fuelAmount);

    public String bookVehicle(String stateNumber, String userName);

    public String closeRenting(String stateNumber, String userName);
}
