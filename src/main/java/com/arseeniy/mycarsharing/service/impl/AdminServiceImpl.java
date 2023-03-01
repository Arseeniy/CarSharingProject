package com.arseeniy.mycarsharing.service.impl;

import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.CloseRentingRequest;
import com.arseeniy.mycarsharing.common.dto.OrderReject;
import com.arseeniy.mycarsharing.common.dto.VehicleCreationRequest;
import com.arseeniy.mycarsharing.common.entity.booking.Order;
import com.arseeniy.mycarsharing.common.entity.booking.OrderStatus;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.OrderRepository;
import com.arseeniy.mycarsharing.repository.UserRepository;
import com.arseeniy.mycarsharing.repository.VehicleRepository;
import com.arseeniy.mycarsharing.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CertainOrderViewer> getOrderListForConfirmation() {

        List<Order> orderForConfirmationList = orderRepository.findByOrderStatus(OrderStatus.AWAITING_CONFIRMATION);

        return orderForConfirmationList.stream().map(order -> new CertainOrderViewer(order.getUsername(),
                order.getStateNumber(),
                order.getVehicleModel(),
                order.getOrderStatus(),
                order.getDayPrice(),
                order.getRentPrice(),
                order.getDamagePrice(),
                order.getDamageDescription(),
                order.getRentingStart(),
                order.getRentingEnd())).toList();
    }

    @Override
    public List<CertainOrderViewer> getOrderListForClose() {

        List<Order> orderForConfirmationList = orderRepository.findByOrderStatus(OrderStatus.AWAITING_FOR_CLOSE);

        return orderForConfirmationList.stream().map(order -> new CertainOrderViewer(order.getUsername(),
                order.getStateNumber(),
                order.getVehicleModel(),
                order.getOrderStatus(),
                order.getDayPrice(),
                order.getRentPrice(),
                order.getDamagePrice(),
                order.getDamageDescription(),
                order.getRentingStart(),
                order.getRentingEnd())).toList();
    }

    // Для админа (подтверждение заказа)
    @Override
    public String confirmOrder(String stateNumber) {

        orderRepository.confirmOrder(stateNumber);

        return "Заказ подтвержден!";
    }

    @Transactional
    @Override
    public String closeVehicleRenting(CloseRentingRequest closeRentingRequest) {

        vehicleRepository.closeVehicleRenting(closeRentingRequest.getStateNumber());
        userRepository.closeRenting(SecurityContextHolder.getContext().getAuthentication().getName());
        orderRepository.closeOrder(closeRentingRequest.getStateNumber());

        return "Аренда успешно закрыта.";
    }

    // Для админа (отклонение заказа)
    @Transactional
    @Override
    public String rejectOrderRequest(OrderReject orderReject) {

        vehicleRepository.closeVehicleRenting(orderReject.getStateNumber());
        userRepository.closeRenting(SecurityContextHolder.getContext().getAuthentication().getName());
        orderRepository.rejectOrder(orderReject.getStateNumber(), orderReject.getRejectDescription());

        return "Заказ отклонен!";
    }

    @Override
    public String vehicleCreation(VehicleCreationRequest requestForCreation) {

        vehicleRepository.save(new Vehicle(requestForCreation.getStateNumber(),
                requestForCreation.getVehicleModel(),
                requestForCreation.getVehicleClass(),
                requestForCreation.getDoorsCount(),
                requestForCreation.getDoorsCount(),
                requestForCreation.isAirConditioning(),
                requestForCreation.isAudio(),
                requestForCreation.getFuelType(),
                requestForCreation.getFuelTankCapacity(),
                requestForCreation.getEnginePower(),
                requestForCreation.getTransmissionType(),
                requestForCreation.isAvailable(),
                requestForCreation.getDayPrice()));

        return "Автомобиль успешно создан.";
    }
}
