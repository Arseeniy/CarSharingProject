package com.arseeniy.mycarsharing.service;


import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.OrderCreationRequest;
import com.arseeniy.mycarsharing.common.dto.OrderHistoryViewer;
import com.arseeniy.mycarsharing.common.dto.OrderReject;
import com.arseeniy.mycarsharing.common.entity.authorization.UsersData;
import com.arseeniy.mycarsharing.common.entity.booking.Fare;
import com.arseeniy.mycarsharing.common.entity.booking.Order;
import com.arseeniy.mycarsharing.common.entity.booking.OrderStatus;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FareRepository fareRepository;

    @Autowired
    private UsersDataRepository usersDataRepository;

    @Override
    public ResponseEntity<String> createOrder(OrderCreationRequest orderCreationRequest) {

        if(orderRepository.existsByStateNumberAndOrderStatus(orderCreationRequest.getStateNumber(), OrderStatus.AWAITING_CONFIRMATION)){
            return ResponseEntity.badRequest().body("Заказ уже был создан!");
        }

        if (orderRepository.existsByStateNumberAndOrderStatus(orderCreationRequest.getStateNumber(), OrderStatus.IN_PROGRESS)){
            return ResponseEntity.badRequest().body("Данный автомобиль уже забронирован!");
        }

        if (!vehicleRepository.existsByStateNumber(orderCreationRequest.getStateNumber())) {
            return ResponseEntity.badRequest().body("Такого автомобиля не существует.");
        }
        if (!orderCreationRequest.getPassportNumber().matches("(\\d{4})[ ](\\d{6})")) {
            return ResponseEntity.badRequest().body("Некорректные номер/серия паспорта.");
        }
        if (orderCreationRequest.getIssuingAuthority().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Некорректные сведения о государственном органе.");
        }
        if (orderCreationRequest.getRegistrationPlace().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Некорректные сведения о месте прописки.");
        }
        if (!orderCreationRequest.getDriverLicenseNumber().matches("(\\d{2})[ ](\\d{2})[ ](\\d{6})")) {
            return ResponseEntity.badRequest().body("Некорректные номер/серия водительского удостоверения.");

        }
        vehicleRepository.bookVehicle(orderCreationRequest.getStateNumber(),
                orderCreationRequest.getRentingStart(), orderCreationRequest.getRentingEnd());

        userRepository.bookVehicle(
                orderCreationRequest.getStateNumber(),
                SecurityContextHolder.getContext().getAuthentication().getName());

        usersDataRepository.save(new UsersData(orderCreationRequest.getFirstName(),
                orderCreationRequest.getLastName(),
                orderCreationRequest.getBirthDate(),
                orderCreationRequest.getPassportNumber(),
                orderCreationRequest.getPassportIssueDate(),
                orderCreationRequest.getIssuingAuthority(),
                orderCreationRequest.getRegistrationPlace(),
                orderCreationRequest.getDriverLicenseNumber(),
                orderCreationRequest.getDriverLicenseIssueDate()));

        Vehicle vehicle = vehicleRepository.findByStateNumber(orderCreationRequest.getStateNumber());

        Fare farePerDay = fareRepository.findByVehicleModel(vehicle.getVehicleModel());

        Double rentPrice = ChronoUnit.DAYS.between(orderCreationRequest.getRentingStart(), orderCreationRequest.getRentingEnd())
                * farePerDay.getDayPrice();

        orderRepository.saveAndFlush(new Order(SecurityContextHolder.getContext().getAuthentication().getName(),
                vehicle.getVehicleModel(),
                orderCreationRequest.getStateNumber(),
                OrderStatus.AWAITING_CONFIRMATION,
                farePerDay.getDayPrice(),
                rentPrice,
                null,
                null,
                null,
                orderCreationRequest.getRentingStart(),
                orderCreationRequest.getRentingEnd()));

        return ResponseEntity.ok("Запрос отправлен!");
    }

    @Override
    public List<CertainOrderViewer> getOrderListForConfirmation() {
        List<Order> orderForConfirmationList = orderRepository.findByOrderStatus(OrderStatus.AWAITING_CONFIRMATION);
        List<CertainOrderViewer> orderViewerList = new ArrayList<>();
        for (Order order : orderForConfirmationList) {
            orderViewerList.add(new CertainOrderViewer(order.getUsername(),
                    order.getStateNumber(),
                    order.getVehicleModel(),
                    order.getOrderStatus(),
                    order.getDayPrice(),
                    order.getRentPrice(),
                    order.getDamagePrice(),
                    order.getDamageDescription(),
                    order.getRentingStart(),
                    order.getRentingEnd()));
        }
        return orderViewerList;
    }

    @Override
    public List<CertainOrderViewer> getOrderListForClose() {
        List<Order> orderForConfirmationList = orderRepository.findByOrderStatus(OrderStatus.AWAITING_FOR_CLOSE);
        List<CertainOrderViewer> orderViewerList = new ArrayList<>();
        for (Order order : orderForConfirmationList) {
            orderViewerList.add(new CertainOrderViewer(order.getUsername(),
                    order.getStateNumber(),
                    order.getVehicleModel(),
                    order.getOrderStatus(),
                    order.getDayPrice(),
                    order.getRentPrice(),
                    order.getDamagePrice(),
                    order.getDamageDescription(),
                    order.getRentingStart(),
                    order.getRentingEnd()));
        }
        return orderViewerList;
    }

    // Для админа (отклонение заказа)
    @Override
    public String rejectOrderRequest(OrderReject orderReject) {

        vehicleRepository.closeVehicleRenting(orderReject.getStateNumber());
        userRepository.closeRenting(SecurityContextHolder.getContext().getAuthentication().getName());
        orderRepository.rejectOrder(orderReject.getStateNumber(), orderReject.getRejectDescription());

        return "Заказ отклонен!";
    }

    // Для админа (подтверждение заказа)
    @Override
    public String confirmOrder(String stateNumber) {

        orderRepository.confirmOrder(stateNumber);

        return "Заказ подтвержден!";
    }

    // Для пользователя (запрос на закрытие аренды)
    @Override
    public String closeRentingRequest(String stateNumber) {

        if (orderRepository.existsByStateNumberAndOrderStatus(stateNumber, OrderStatus.IN_PROGRESS) &&
                orderRepository.findByStateNumberAndOrderStatus(stateNumber, OrderStatus.IN_PROGRESS).getUsername()
                        .equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            orderRepository.closeRentingRequest(stateNumber);
        } else
            return "Такого заказа не существует./Заказ числится за другим пользователем./Заказа нет в списке пдтвержденных.";

        return "Запрос отправлен!";
    }

    @Override
    public List<OrderHistoryViewer> getOrderHistory() {
        List<Order> orderList = orderRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<OrderHistoryViewer> orderHistoryViewers = new ArrayList<>();
        for (Order order : orderList) {
            orderHistoryViewers.add(new OrderHistoryViewer(order.getStateNumber(),
                    order.getVehicleModel(),
                    order.getRentingStart(),
                    order.getRentingEnd()));
        }

        return orderHistoryViewers;
    }

    @Override
    public CertainOrderViewer getCertainOrder(OrderHistoryViewer orderHistoryViewer) {

        Order order = orderRepository.getCertainOrder(SecurityContextHolder.getContext().getAuthentication().getName(),
                orderHistoryViewer.getRentingStart());


        return new CertainOrderViewer(order.getUsername(),
                order.getStateNumber(),
                order.getVehicleModel(),
                order.getOrderStatus(),
                order.getDayPrice(),
                order.getRentPrice(),
                order.getDamagePrice(),
                order.getDamageDescription(),
                order.getRentingStart(),
                order.getRentingEnd());
    }
}
