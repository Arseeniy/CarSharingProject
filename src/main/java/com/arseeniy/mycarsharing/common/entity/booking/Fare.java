package com.arseeniy.mycarsharing.common.entity.booking;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "fare_library")
public class Fare {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "fareIdSeq", sequenceName = "fare_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fareIdSeq")
    private Integer id;

    @Column(name = "vehicle_model")
    private String vehicleModel;

    @Column(name = "day_price")
    private Double dayPrice;

    public Fare(String vehicleModel, Double dayPrice) {
        this.vehicleModel = vehicleModel;
        this.dayPrice = dayPrice;
    }
}
