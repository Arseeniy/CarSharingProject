package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.OrderCreationRequest;
import com.arseeniy.mycarsharing.common.dto.OrderHistoryViewer;
import com.arseeniy.mycarsharing.common.dto.OrderReject;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface OrderService {

    public ResponseEntity<String> createOrder(OrderCreationRequest orderCreationRequest);

    public String rejectOrderRequest(OrderReject orderReject);

    public String confirmOrder(String stateNumber);

    public String closeRentingRequest(String stateNumber);

    public CertainOrderViewer getCertainOrder(OrderHistoryViewer orderHistoryViewer);

    public List<OrderHistoryViewer> getOrderHistory();

    public List<CertainOrderViewer> getOrderListForConfirmation();

    public List<CertainOrderViewer> getOrderListForClose();

}
