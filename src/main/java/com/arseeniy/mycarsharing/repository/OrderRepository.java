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

    @Transactional
    @Query(value = "INSERT INTO orders_library (id, username, state_number, " +
            "vehicle_model, order_status, day_price, rent_price," +
            "renting_start, renting_end) VALUES (1, :username, :stateNumber, :vehicleModel, :orderStatus, :dayPrice, :rentPrice, " +
            ":rentingStart, :rentingEnd)", nativeQuery = true)
    void createOrder(@Param("username") String username,
                     @Param("stateNumber") String stateNumber,
                     @Param("vehicleModel") String vehicleModel,
                     @Param("orderStatus") OrderStatus orderStatus,
                     @Param("dayPrice") Double dayPrice,
                     @Param("rentPrice") Double rentPrice,
                     @Param("rentingStart") LocalDate rentingStart,
                     @Param("rentingEnd") LocalDate rentingEnd);

    //Для истории заказов пользователю
    List<Order> findByUsername(String username);

    //Для создания запроса на закрытие заказа
    Order findByStateNumberAndOrderStatus(String stateNumber, OrderStatus orderStatus);

    //Для получения определенного заказа из истории
    Order findByStateNumberAndRentingStart(String stateNumber, LocalDate rentingStart);

    Boolean existsByStateNumberAndOrderStatus(String stateNumber, OrderStatus orderStatus);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders_library SET order_status = AWAITING_FOR_CLOSE WHERE state_number = :stateNumber and order_status = IN_PROGRESS", nativeQuery = true)
    void closeRentingRequest(@Param("stateNumber") String stateNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders_library SET order_status = CLOSED WHERE state_number = :stateNumber and order_status = AWAITING_FOR_CLOSE", nativeQuery = true)
    void closeOrder(@Param("stateNumber") String stateNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders_library SET order_status = REJECTED, reject_description = :rejectDescription" +
            " WHERE state_number = :stateNumber and order_status = AWAITING_CONFIRMATION", nativeQuery = true)
    void rejectOrder(@Param("stateNumber") String stateNumber, @Param("rejectDescription") String rejectDescription);
}
