package com.arseeniy.mycarsharing.common.entity.booking;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders_library")
public class Order {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientIdSeq", sequenceName = "client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientIdSeq")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "vehicle_model")
    private String vehicleModel;

    @Column(name = "state_number")
    private String stateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "day_price")
    private Double dayPrice;

    @Column(name = "rent_price")
    private Double rentPrice;

    @Column(name = "reject_description)")
    private String rejectDescription;

    @Column(name = "damage_price")
    private Double damagePrice;

    @Column(name = "damage_description")
    private String damageDescription;

    @Column(name = "renting_start")
    private LocalDate rentingStart;

    @Column(name = "renting_end")
    private LocalDate rentingEnd;

    public Order(String username, String stateNumber, String vehicleModel, OrderStatus orderStatus,
                 Double dayPrice, Double rentPrice, LocalDate rentingStart, LocalDate rentingEnd) {
        this.username = username;
        this.stateNumber = stateNumber;
        this.vehicleModel = vehicleModel;
        this.orderStatus = orderStatus;
        this.dayPrice = dayPrice;
        this.rentPrice = rentPrice;
        this.rentingStart = rentingStart;
        this.rentingEnd = rentingEnd;
    }
}
