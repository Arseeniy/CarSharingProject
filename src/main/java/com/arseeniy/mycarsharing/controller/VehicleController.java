package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.AvailableVehicle;
import com.arseeniy.mycarsharing.common.dto.BookingVehicle;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import com.arseeniy.mycarsharing.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/getAvailable")
    public List<AvailableVehicle> getAvailableVehicleList() {

        return vehicleService.getAvailableVehicleList(vehicleRepository.findAll());
    }

    @GetMapping("/getAvailableWithDates")
    public List<AvailableVehicle> getAvailableVehicleListByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return vehicleService.getAvailableVehicleListByDate(vehicleRepository.findAll(), startDate, endDate);
    }

    //Выбор конкретного авто для бронирования
    @GetMapping("/chooseForBooking/{stateNumber}")
    public BookingVehicle chooseForBooking(@PathVariable String stateNumber) {

        return vehicleService.chooseForBooking(stateNumber);
    }

    @GetMapping("/getByVehicleModel/{vehicleModel}")
    public List<Vehicle> getCertainModel(@PathVariable String vehicleModel) {

        return vehicleRepository.findAllByVehicleModel(vehicleModel);
    }
}
