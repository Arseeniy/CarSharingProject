package com.arseeniy.mycarsharing.controller;

import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.OrderCreationRequest;
import com.arseeniy.mycarsharing.common.dto.OrderHistoryViewer;
import com.arseeniy.mycarsharing.repository.OrderRepository;
import com.arseeniy.mycarsharing.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    //Оформление заказа
    @PostMapping("/createOrder")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderCreationRequest orderCreationRequest) {

        return ResponseEntity.ok(orderService.createOrder(orderCreationRequest));
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
    @PostMapping("/getCertainOrder")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('USER') or hasRole('SUPPORT') or hasRole('ADMIN')")
    public CertainOrderViewer getCertainOrder(@Valid @RequestBody OrderHistoryViewer orderHistoryViewer) {

        return orderService.getCertainOrder(orderHistoryViewer);
    }
}
