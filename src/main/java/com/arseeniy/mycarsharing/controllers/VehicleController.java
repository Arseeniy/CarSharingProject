package com.arseeniy.mycarsharing.controllers;

import com.arseeniy.mycarsharing.common.VehicleViewer;
import com.arseeniy.mycarsharing.entity.Vehicle;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import com.arseeniy.mycarsharing.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleService vehicleService;


    @GetMapping("/getAvailable")
    public List<VehicleViewer> getAvailableVehicleList() {
        return vehicleService.getAvailableVehicleList(vehicleRepository.findAll());
    }

    @GetMapping("/getByNumber/{stateNumber}")
    public Vehicle getByStateNumber(@PathVariable String stateNumber) {
        return vehicleRepository.findByStateNumber(stateNumber);
    }

    @PostMapping("/book/{stateNumber}")
    public String bookVehicle(@PathVariable String stateNumber, @PathVariable String userName) {
        return vehicleService.bookVehicle(stateNumber, userName);
    }

    @PostMapping("/closeRenting/{stateNumber}/userName")
    public String closeVehicleRenting(@PathVariable String stateNumber, @PathVariable String userName) {
        return vehicleService.closeRenting(stateNumber, userName);
    }

    @GetMapping("/getByVehicleModel/{vehicleModel}")
    public List<Vehicle> getCertainModel(@PathVariable String vehicleModel) {
        return vehicleRepository.findAllByVehicleModel(vehicleModel);
    }

    @DeleteMapping("/deleteByNumber")
    public void deleteByStateNumber(@PathVariable String stateNumber) {
        vehicleRepository.deleteByStateNumber(stateNumber);
    }

    @PostMapping("/create")
    public void createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleModel("hyundai");
        vehicle.setStateNumber("j569gr18");
        vehicle.setFuelAmount(12);
        vehicle.setStatus(false);
        vehicle.setDistanceForVehicle(200);
        vehicleRepository.save(vehicle);
    }

}