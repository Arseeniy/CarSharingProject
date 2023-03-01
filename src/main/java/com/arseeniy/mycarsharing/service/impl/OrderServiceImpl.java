package com.arseeniy.mycarsharing.service.impl;


import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.OrderCreationRequest;
import com.arseeniy.mycarsharing.common.dto.OrderHistoryViewer;
import com.arseeniy.mycarsharing.common.entity.authorization.UsersData;
import com.arseeniy.mycarsharing.common.entity.booking.Order;
import com.arseeniy.mycarsharing.common.entity.booking.OrderStatus;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.exception.CustomException;
import com.arseeniy.mycarsharing.repository.*;
import com.arseeniy.mycarsharing.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersDataRepository usersDataRepository;

    private void validateOrderRequest(OrderCreationRequest orderCreationRequest) {

        if (orderRepository.existsByStateNumberAndOrderStatus(orderCreationRequest.getStateNumber(), OrderStatus.AWAITING_CONFIRMATION)) {
            throw new CustomException("Заказ уже был создан!");
        }

        if (orderRepository.existsByStateNumberAndOrderStatus(orderCreationRequest.getStateNumber(), OrderStatus.IN_PROGRESS)) {
            throw new CustomException("Данный автомобиль уже забронирован!");
        }

        if (!vehicleRepository.existsByStateNumber(orderCreationRequest.getStateNumber())) {
            throw new CustomException("Такого автомобиля не существует.");
        }
        if (!orderCreationRequest.getPassportNumber().matches("(\\d{4})[ ](\\d{6})")) {
            throw new CustomException("Некорректные номер/серия паспорта.");
        }
        if (orderCreationRequest.getIssuingAuthority().trim().isEmpty()) {
            throw new CustomException("Некорректные сведения о государственном органе.");
        }
        if (orderCreationRequest.getRegistrationPlace().trim().isEmpty()) {
            throw new CustomException("Некорректные сведения о месте прописки.");
        }
        if (!orderCreationRequest.getDriverLicenseNumber().matches("(\\d{2})[ ](\\d{2})[ ](\\d{6})")) {
            throw new CustomException("Некорректные номер/серия водительского удостоверения.");
        }
    }

    @Transactional
    @Override
    public String createOrder(OrderCreationRequest orderCreationRequest) {

        validateOrderRequest(orderCreationRequest);

        vehicleRepository.bookVehicle(orderCreationRequest.getStateNumber(),
                orderCreationRequest.getRentingStart(), orderCreationRequest.getRentingEnd());

        log.info("Order creation: vehicle was booked.");

        userRepository.bookVehicle(
                orderCreationRequest.getStateNumber(),
                SecurityContextHolder.getContext().getAuthentication().getName());

        log.info("Order creation: user was updated.");

        usersDataRepository.saveAndFlush(new UsersData(orderCreationRequest.getFirstName(),
                orderCreationRequest.getLastName(),
                orderCreationRequest.getBirthDate(),
                orderCreationRequest.getPassportNumber(),
                orderCreationRequest.getPassportIssueDate(),
                orderCreationRequest.getIssuingAuthority(),
                orderCreationRequest.getRegistrationPlace(),
                orderCreationRequest.getDriverLicenseNumber(),
                orderCreationRequest.getDriverLicenseIssueDate()));

        log.info("Order creation: users data was created.");

        Vehicle vehicle = vehicleRepository.findByStateNumber(orderCreationRequest.getStateNumber());

        Double rentPrice = ChronoUnit.DAYS.between(orderCreationRequest.getRentingStart(), orderCreationRequest.getRentingEnd())
                * vehicle.getDayPrice();

        orderRepository.saveAndFlush(new Order(SecurityContextHolder.getContext().getAuthentication().getName(),
                vehicle.getVehicleModel(),
                orderCreationRequest.getStateNumber(),
                OrderStatus.AWAITING_CONFIRMATION,
                vehicle.getDayPrice(),
                rentPrice,
                null,
                null,
                null,
                orderCreationRequest.getRentingStart(),
                orderCreationRequest.getRentingEnd()));

        return "Запрос отправлен!";
    }

    private Boolean isAvailableForClose(String stateNumber) {

        return orderRepository.existsByStateNumberAndOrderStatus(stateNumber, OrderStatus.IN_PROGRESS) &&
                orderRepository.findByStateNumberAndOrderStatus(stateNumber, OrderStatus.IN_PROGRESS).getUsername()
                        .equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }


    // Для пользователя (запрос на закрытие аренды)
    @Override
    public String closeRentingRequest(String stateNumber) {

        if (isAvailableForClose(stateNumber)) {
            orderRepository.closeRentingRequest(stateNumber);
        } else
            return "Такого заказа не существует./Заказ числится за другим пользователем./Заказа нет в списке пдтвержденных.";

        return "Запрос отправлен!";
    }

    @Override
    public List<OrderHistoryViewer> getOrderHistory() {

        List<Order> orderList = orderRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return orderList.stream().map(order -> new OrderHistoryViewer(order.getStateNumber(),
                order.getVehicleModel(),
                order.getRentingStart(),
                order.getRentingEnd())).toList();
    }

    @Override
    public CertainOrderViewer getCertainOrder(OrderHistoryViewer orderHistoryViewer) {

        Order order = orderRepository.getCertainOrder(orderHistoryViewer.getStateNumber(),
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
