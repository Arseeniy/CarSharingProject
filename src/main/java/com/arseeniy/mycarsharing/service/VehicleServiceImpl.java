package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.VehicleForBooking;
import com.arseeniy.mycarsharing.common.dto.VehicleViewer;
import com.arseeniy.mycarsharing.common.entity.booking.Fare;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.FareRepository;
import com.arseeniy.mycarsharing.repository.UserRepository;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FareRepository fareRepository;

    @Override
    public int getVehicleRunningDistance(int fuelAmount) {
        return fuelAmount * 10;
    }

    @Override
    public VehicleForBooking chooseForBooking(String stateNumber) {

        Vehicle vehicle = vehicleRepository.findByStateNumber(stateNumber);

        Fare fare = fareRepository.findByVehicleModel(
                vehicleRepository.findByStateNumber(stateNumber).getVehicleModel());

        VehicleForBooking bookingVehicle = new VehicleForBooking(stateNumber,
                vehicle.getVehicleModel(),
                getVehicleRunningDistance(vehicle.getFuelAmount()),
                fare.getMinutePrice(),
                fare.getHourPrice(),
                fare.getDayPrice());

        return bookingVehicle;
    }

    @Override
    public String bookVehicle(String stateNumber, String username) {
        if (!userRepository.existsByUsername(username)) {
            return "User" + "\"" + username + "\"" + "does not exist!";
        }
        vehicleRepository.bookVehicle(stateNumber);
        userRepository.bookVehicle(stateNumber, username);
        return "Автомобиль забронирован!";
    }

    @Override
    public String closeRenting(String stateNumber, String userName) {
        vehicleRepository.closeRenting(stateNumber);
        userRepository.closeRenting(userName);
        return "Поездка завершена!";
    }

    @Override
    public List<VehicleViewer> getAvailableVehicleList(List<Vehicle> vehicleList) {
        List<VehicleViewer> vehicleViewerList = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            if (!vehicle.isStatus()) {
                vehicleViewerList.add(new VehicleViewer(vehicle.getVehicleModel(),
                        getVehicleRunningDistance(vehicle.getFuelAmount()), vehicle.getDistanceForVehicle()));
            }
        }
        return vehicleViewerList;
    }
}
