package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;

import java.util.Date;
import java.util.List;

public interface VehicleService {



    public List<AvailableVehicle> getAvailableVehicleList(List<Vehicle> vehicleList);

    public List<AvailableVehicle> getAvailableVehicleListByDate(List<Vehicle> vehicles, Date startDate, Date endDate);

    public BookingVehicle chooseForBooking(String stateNumber);



}
