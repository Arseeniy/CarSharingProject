package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.OrderRepository;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import com.arseeniy.mycarsharing.service.OrderService;
import com.arseeniy.mycarsharing.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/getAvailable")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<AvailableVehicleViewer> getAvailableVehicleList() {

        return vehicleService.getAvailableVehicleList(vehicleRepository.findAll());
    }

    @GetMapping("/getAvailableWithDates")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<AvailableVehicleViewer> getAvailableVehicleListByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return vehicleService.getAvailableVehicleListByDate(vehicleRepository.findAll(), startDate, endDate);
    }

    //Выбор конкретного авто для бронирования
    @GetMapping("/chooseForBooking/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public BookingVehicleViewer chooseForBooking(@PathVariable String stateNumber) {

        return vehicleService.chooseForBooking(stateNumber);
    }

    //Оформление заказа
    @PostMapping("/createOrder")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderCreationRequest orderCreationRequest) {

        return vehicleService.createOrder(orderCreationRequest);
    }

    //Запрос на досрочное завершение заказа
    @PatchMapping("/closeRentingRequest/{stateNumber}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public String closeRentingRequest(@PathVariable String stateNumber) {

        return orderService.closeRentingRequest(stateNumber);
    }

    //Получить историю заказов
    @GetMapping("/getOrderHistory")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<OrderHistoryViewer> getOrderHistory() {

        return orderService.getOrderHistory();
    }

    //Достать определенный заказ из истории
    @GetMapping("/getCertainOrder")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public CertainOrderViewer getCertainOrder(@Valid @RequestBody OrderHistoryViewer orderHistoryViewer) {

        return orderService.getCertainOrder(orderHistoryViewer);
    }

    @GetMapping("/getByVehicleModel/{vehicleModel}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public List<Vehicle> getCertainModel(@PathVariable String vehicleModel) {

        return vehicleRepository.findAllByVehicleModel(vehicleModel);
    }

}