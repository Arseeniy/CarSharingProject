package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.CloseRentingRequest;
import com.arseeniy.mycarsharing.common.dto.OrderReject;
import com.arseeniy.mycarsharing.common.dto.VehicleCreationRequest;

import java.util.List;

public interface AdminService {

    public List<CertainOrderViewer> getOrderListForConfirmation();

    public List<CertainOrderViewer> getOrderListForClose();

    public String confirmOrder(String stateNumber);

    public String closeVehicleRenting(CloseRentingRequest closeRentingRequest);

    public String rejectOrderRequest(OrderReject orderReject);

    public String vehicleCreation(VehicleCreationRequest requestForCreation);
}
