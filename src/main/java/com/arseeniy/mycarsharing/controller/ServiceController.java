package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import com.arseeniy.mycarsharing.service.OrderService;
import com.arseeniy.mycarsharing.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private OrderService orderService;

    // Список заказов на подтверждение
    @GetMapping("/getConfirmationList")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<CertainOrderViewer> getOrderListForConfirmation() {

        return orderService.getOrderListForConfirmation();
    }

    // Список заказов на закрытие
    @GetMapping("/getCloseList")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<CertainOrderViewer> getOrderListForClose() {

        return orderService.getOrderListForClose();

    }

    @GetMapping("/getCertainOrder")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public CertainOrderViewer getCertainOrder(@Valid @RequestBody OrderHistoryViewer orderHistoryViewer) {

        return orderService.getCertainOrder(orderHistoryViewer);
    }

    // Подтверждение заказа
    @PatchMapping("/confirmOrder/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String confirmOrder(@PathVariable String stateNumber) {

        return orderService.confirmOrder(stateNumber);

    }

    // Закрытие аренды
    @PatchMapping("/closeVehicleRenting")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String closeVehicleRenting(@Valid @RequestBody CloseRentingRequest closeRentingRequest) {

        return vehicleService.closeVehicleRenting(closeRentingRequest);
    }

    // Отклонение запроса на аренду
    @PatchMapping("/rejectOrder")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String rejectOrder(@Valid @RequestBody OrderReject orderReject) {

        return orderService.rejectOrderRequest(orderReject);
    }


    @DeleteMapping("/deleteVehicle/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public void deleteByStateNumber(@PathVariable String stateNumber) {

        vehicleRepository.deleteByStateNumber(stateNumber);
    }

    @PostMapping("/createVehicle")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String createVehicle(@Valid @RequestBody RequestForVehicleCreation requestForCreation) {

        vehicleService.vehicleCreation(requestForCreation);

        return "Vehicle was successfully created";
    }

}
