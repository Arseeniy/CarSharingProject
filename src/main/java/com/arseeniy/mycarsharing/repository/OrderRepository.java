package com.arseeniy.mycarsharing.repository;


import com.arseeniy.mycarsharing.common.entity.booking.Order;
import com.arseeniy.mycarsharing.common.entity.booking.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //Для истории заказов пользователю
    List<Order> findByUsername(String username);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    //Для создания запроса на закрытие заказа
    Order findByStateNumberAndOrderStatus(String stateNumber, OrderStatus orderStatus);

    @Query(value = "SELECT * FROM orders_library WHERE state_number = :stateNumber AND renting_start = :rentingStart", nativeQuery = true)
    Order getCertainOrder(@Param("stateNumber") String stateNumber, @Param("rentingStart") LocalDate rentingStart);

    //Для получения определенного заказа из истории
    Order findByStateNumberAndRentingStart(String stateNumber, LocalDate rentingStart);


    Boolean existsByStateNumberAndOrderStatus(String stateNumber, OrderStatus orderStatus);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders_library SET order_status = 5 WHERE state_number = :stateNumber and order_status = 1", nativeQuery = true)
    void closeRentingRequest(@Param("stateNumber") String stateNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders_library SET order_status = 3 WHERE state_number = :stateNumber and order_status = 5", nativeQuery = true)
    void closeOrder(@Param("stateNumber") String stateNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders_library SET order_status = 4, reject_description = :rejectDescription" +
            " WHERE state_number = :stateNumber and order_status = 0", nativeQuery = true)
    void rejectOrder(@Param("stateNumber") String stateNumber, @Param("rejectDescription") String rejectDescription);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders_library SET order_status = 1" +
            " WHERE state_number = :stateNumber and order_status = 0", nativeQuery = true)
    void confirmOrder(@Param("stateNumber") String stateNumber);
}
