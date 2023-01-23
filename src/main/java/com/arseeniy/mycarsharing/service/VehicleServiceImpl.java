package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.VehicleViewer;
import com.arseeniy.mycarsharing.entity.Vehicle;
import com.arseeniy.mycarsharing.repository.ClientRepository;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public int getVehicleRunningDistance(int fuelAmount) {
        return fuelAmount * 10;
    }

    @Override
    public String bookVehicle(String stateNumber, String userName) {
        vehicleRepository.findByStateNumber(stateNumber).setStatus(true);
        clientRepository.findByUserName(userName).setCurrentVehicle(stateNumber);
        return "Автомобиль забронирован!";
    }

    @Override
    public String closeRenting(String stateNumber, String userName) {
        vehicleRepository.findByStateNumber(stateNumber).setStatus(false);
        clientRepository.findByUserName(userName).setCurrentVehicle(null);
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
