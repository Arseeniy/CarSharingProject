package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.CertainOrderViewer;
import com.arseeniy.mycarsharing.common.dto.OrderHistoryViewer;


import java.util.List;

public interface OrderService {

    public String closeRentingRequest(String stateNumber);

    public CertainOrderViewer getCertainOrder(OrderHistoryViewer orderHistoryViewer);

    public List<OrderHistoryViewer> getOrderHistory();

}
