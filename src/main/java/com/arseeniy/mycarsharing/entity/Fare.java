package com.arseeniy.mycarsharing.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fare_library")
public class Fare {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "fareIdSeq", sequenceName = "fare_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fareIdSeq")
    private Integer id;

    @Column(name = "vehicleModel")
    private String vehicleModel;

    @Column(name = "minutePrice")
    private Integer minutePrice;

    @Column(name = "hourPrice")
    private Integer hourPrice;

    @Column(name = "dayPrice")
    private Integer dayPrice;

}
