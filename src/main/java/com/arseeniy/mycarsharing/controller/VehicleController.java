package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.RequestForVehicleCreation;
import com.arseeniy.mycarsharing.common.dto.VehicleForBooking;
import com.arseeniy.mycarsharing.common.dto.VehicleViewer;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import com.arseeniy.mycarsharing.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/getAvailable")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<VehicleViewer> getAvailableVehicleList() {
        return vehicleService.getAvailableVehicleList(vehicleRepository.findAll());
    }

    @GetMapping("/chooseForBooking/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public VehicleForBooking chooseForBooking(@PathVariable String stateNumber) {
        return vehicleService.chooseForBooking(stateNumber);
    }

    @PatchMapping("/book/{stateNumber}/{username}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String bookVehicle(@PathVariable String stateNumber, String username) {
        return vehicleService.bookVehicle(stateNumber, username);
    }

    @PatchMapping("/closeRenting/{stateNumber}/{userName}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String closeVehicleRenting(@PathVariable String stateNumber, @PathVariable String userName) {
        return vehicleService.closeRenting(stateNumber, userName);
    }

    @GetMapping("/getByVehicleModel/{vehicleModel}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<Vehicle> getCertainModel(@PathVariable String vehicleModel) {
        return vehicleRepository.findAllByVehicleModel(vehicleModel);
    }

    @DeleteMapping("/deleteByStateNumber/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('SUPPORT') or hasRole('ADMIN')")
    public void deleteByStateNumber(@PathVariable String stateNumber) {
        vehicleRepository.deleteByStateNumber(stateNumber);
    }

    @PostMapping("/create")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('SUPPORT') or hasRole('ADMIN')")
    public String createVehicle(@Valid @RequestBody RequestForVehicleCreation requestForVehicleCreation) {
        vehicleRepository.save(new Vehicle(requestForVehicleCreation.getStateNumber(), requestForVehicleCreation.getVehicleModel(),
                requestForVehicleCreation.getFuelAmount(), requestForVehicleCreation.getDistanceForVehicle()));
        return "Vehicle was successfully created";
    }

}