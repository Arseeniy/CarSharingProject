package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface VehicleService {

    public String vehicleCreation(RequestForVehicleCreation requestForCreation);

    public List<AvailableVehicleViewer> getAvailableVehicleList(List<Vehicle> vehicleList);

    public List<AvailableVehicleViewer> getAvailableVehicleListByDate(List<Vehicle> vehicles, Date startDate, Date endDate);

    public BookingVehicleViewer chooseForBooking(String stateNumber);

    public ResponseEntity<String> createOrder(OrderCreationRequest orderCreationRequest);

    public String closeVehicleRenting(CloseRentingRequest closeRentingRequest);

    public String rejectVehicleRenting(OrderReject orderReject);

}
