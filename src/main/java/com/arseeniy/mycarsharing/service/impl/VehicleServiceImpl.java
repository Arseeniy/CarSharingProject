package com.arseeniy.mycarsharing.service.impl;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.*;
import com.arseeniy.mycarsharing.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<AvailableVehicle> getAvailableVehicleList(List<Vehicle> vehicleList) {

        List<AvailableVehicle> availableVehicleList = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.isAvailable()) {
                availableVehicleList.add(new AvailableVehicle(vehicle.getStateNumber(),
                        vehicle.getVehicleModel(),
                        vehicle.getVehicleClass(),
                        vehicle.getTransmissionType(),
                        vehicle.getDayPrice()));
            }
        }
        return availableVehicleList;
    }

    @Override
    public List<AvailableVehicle> getAvailableVehicleListByDate(List<Vehicle> vehicles, Date startDate, Date endDate) {
        List<AvailableVehicle> availableVehicleArrayList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable() || vehicle.getRentingEnd().before(startDate)) {
                availableVehicleArrayList.add(new AvailableVehicle(vehicle.getStateNumber(),
                        vehicle.getVehicleModel(),
                        vehicle.getVehicleClass(),
                        vehicle.getTransmissionType(),
                        vehicle.getDayPrice()));
            }
        }
        return availableVehicleArrayList;
    }

    @Override
    public BookingVehicle chooseForBooking(String stateNumber) {

        Vehicle vehicle = vehicleRepository.findByStateNumber(stateNumber);

        return new BookingVehicle(stateNumber,
                vehicle.getVehicleModel(),
                vehicle.getVehicleClass(),
                vehicle.getDoorsCount(),
                vehicle.getPassengersCount(),
                vehicle.isAirConditioning(),
                vehicle.isAudio(),
                vehicle.getFuelType(),
                vehicle.getFuelTankCapacity(),
                vehicle.getEnginePower(),
                vehicle.getTransmissionType(),
                vehicle.getDayPrice());
    }
}
