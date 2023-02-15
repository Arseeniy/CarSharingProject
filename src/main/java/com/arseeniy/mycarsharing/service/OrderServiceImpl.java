package com.arseeniy.mycarsharing.service;


import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.OrderHistoryViewer;
import com.arseeniy.mycarsharing.common.entity.booking.Order;
import com.arseeniy.mycarsharing.common.entity.booking.OrderStatus;
import com.arseeniy.mycarsharing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public String closeRentingRequest(String stateNumber) {

        if (orderRepository.existsByStateNumberAndOrderStatus(stateNumber, OrderStatus.IN_PROGRESS) &&
                orderRepository.findByStateNumberAndOrderStatus(stateNumber, OrderStatus.IN_PROGRESS).getUsername()
                        .equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            orderRepository.closeRentingRequest(stateNumber);
        } else return "Такого заказа не существует./Заказ числится за другим пользователем.";

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

        Order order = orderRepository.findByStateNumberAndRentingStart(SecurityContextHolder.getContext().getAuthentication().getName(),
                orderHistoryViewer.getRentingStart());

        return new CertainOrderViewer(order.getUsername(),
                order.getStateNumber(),
                order.getOrderStatus(),
                order.getDayPrice(),
                order.getRentPrice(),
                order.getDamagePrice(),
                order.getDamageDescription(),
                order.getRentingStart(),
                order.getRentingEnd());

    }
}
