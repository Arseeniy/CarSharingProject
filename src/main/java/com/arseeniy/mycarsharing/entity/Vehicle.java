package com.arseeniy.mycarsharing.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vehicle_library")
public class Vehicle {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "vehicleIdSeq", sequenceName = "vehicle_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicleIdSeq")
    private Long id;

    @Column(name = "state_number")
    private String stateNumber;

    @Column(name = "vehicle_model")
    private String vehicleModel;

    @Column(name = "fuel_amount")
    private int fuelAmount;

    @Column(name = "distance_for_vehicle")
    private int distanceForVehicle;

    @Column(name = "status")
    private boolean status;

}
