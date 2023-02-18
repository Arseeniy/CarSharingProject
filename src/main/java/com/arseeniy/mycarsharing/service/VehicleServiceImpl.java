package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.common.entity.booking.Fare;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
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
    private FareRepository fareRepository;

    @Autowired
    private UsersDataRepository usersDataRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<AvailableVehicleViewer> getAvailableVehicleList(List<Vehicle> vehicleList) {

        List<AvailableVehicleViewer> availableVehicleViewerList = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.isAvailable()) {
                availableVehicleViewerList.add(new AvailableVehicleViewer(vehicle.getStateNumber(),
                        vehicle.getVehicleModel(),
                        vehicle.getVehicleClass(),
                        vehicle.getTransmissionType(),
                        fareRepository.findByVehicleModel(vehicle.getVehicleModel()).getDayPrice()));
            }
        }
        return availableVehicleViewerList;
    }

    @Override
    public List<AvailableVehicleViewer> getAvailableVehicleListByDate(List<Vehicle> vehicles, Date startDate, Date endDate) {
        List<AvailableVehicleViewer> availableVehicleViewerArrayList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable() || vehicle.getRentingEnd().before(startDate)) {
                availableVehicleViewerArrayList.add(new AvailableVehicleViewer(vehicle.getStateNumber(),
                        vehicle.getVehicleModel(),
                        vehicle.getVehicleClass(),
                        vehicle.getTransmissionType(),
                        fareRepository.findByVehicleModel(vehicle.getVehicleModel()).getDayPrice()));
            }
        }
        return availableVehicleViewerArrayList;
    }

    @Override
    public BookingVehicleViewer chooseForBooking(String stateNumber) {

        Vehicle vehicle = vehicleRepository.findByStateNumber(stateNumber);

        Fare farePerDay = fareRepository.findByVehicleModel(vehicle.getVehicleModel());

        return new BookingVehicleViewer(stateNumber,
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
                farePerDay.getDayPrice());
    }

    @Override
    public String closeVehicleRenting(CloseRentingRequest closeRentingRequest) {

        vehicleRepository.closeVehicleRenting(closeRentingRequest.getStateNumber());
        userRepository.closeRenting(SecurityContextHolder.getContext().getAuthentication().getName());
        orderRepository.closeOrder(closeRentingRequest.getStateNumber());

        return "Аренда успешно закрыта.";
    }

    @Override
    public String vehicleCreation(RequestForVehicleCreation requestForCreation) {

        vehicleRepository.save(new Vehicle(requestForCreation.getStateNumber(),
                requestForCreation.getVehicleModel(),
                requestForCreation.getVehicleClass(),
                requestForCreation.getDoorsCount(),
                requestForCreation.getDoorsCount(),
                requestForCreation.isAirConditioning(),
                requestForCreation.isAudio(),
                requestForCreation.getFuelType(),
                requestForCreation.getFuelTankCapacity(),
                requestForCreation.getEnginePower(),
                requestForCreation.getTransmissionType(),
                requestForCreation.isAvailable()));

        return "Автомобиль успешно создан.";
    }
}
