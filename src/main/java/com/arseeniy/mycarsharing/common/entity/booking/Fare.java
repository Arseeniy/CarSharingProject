package com.arseeniy.mycarsharing.common.entity.booking;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Double minutePrice;

    @Column(name = "hourPrice")
    private Double hourPrice;

    @Column(name = "dayPrice")
    private Double dayPrice;

}
