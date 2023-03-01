package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import com.arseeniy.mycarsharing.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private AdminService adminService;

    // Список заказов на подтверждение
    @GetMapping("/getConfirmationList")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<CertainOrderViewer> getOrderListForConfirmation() {

        return adminService.getOrderListForConfirmation();
    }

    // Список заказов на закрытие
    @GetMapping("/getCloseList")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public List<CertainOrderViewer> getOrderListForClose() {

        return adminService.getOrderListForClose();

    }

    // Подтверждение заказа
    @PatchMapping("/confirmOrder/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public String confirmOrder(@PathVariable String stateNumber) {

        return adminService.confirmOrder(stateNumber);

    }

    // Закрытие аренды
    @PatchMapping("/closeVehicleRenting")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public String closeVehicleRenting(@Valid @RequestBody CloseRentingRequest closeRentingRequest) {

        return adminService.closeVehicleRenting(closeRentingRequest);
    }

    // Отклонение запроса на аренду
    @PatchMapping("/rejectOrder")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public String rejectOrder(@Valid @RequestBody OrderReject orderReject) {

        return adminService.rejectOrderRequest(orderReject);
    }


    @DeleteMapping("/deleteVehicle/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public void deleteByStateNumber(@PathVariable String stateNumber) {

        vehicleRepository.deleteByStateNumber(stateNumber);
    }

    @PostMapping("/createVehicle")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public String createVehicle(@Valid @RequestBody VehicleCreationRequest requestForCreation) {

        adminService.vehicleCreation(requestForCreation);

        return "Vehicle was successfully created";
    }

}
