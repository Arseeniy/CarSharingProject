package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.VehicleDto;
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
    public Vehicle chooseForBooking(@PathVariable String stateNumber) {
        return vehicleRepository.findByStateNumber(stateNumber);
    }

    @PatchMapping("/book/{stateNumber}/{userName}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String bookVehicle(@PathVariable String stateNumber, @PathVariable String userName) {
        return vehicleService.bookVehicle(stateNumber, userName);
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

    @DeleteMapping("/deleteByNumber")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('SUPPORT') or hasRole('ADMIN')")
    public void deleteByStateNumber(@PathVariable String stateNumber) {
        vehicleRepository.deleteByStateNumber(stateNumber);
    }

    @PostMapping("/create")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('SUPPORT') or hasRole('ADMIN')")
    public String createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        vehicleRepository.save(new Vehicle(vehicleDto.getStateNumber(), vehicleDto.getVehicleModel(),
                vehicleDto.getFuelAmount(), vehicleDto.getDistanceForVehicle()));
        return "Vehicle was successfully created";
    }

}