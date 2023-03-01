package com.arseeniy.mycarsharing.common.entity.booking;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicle_library", uniqueConstraints = {
        @UniqueConstraint(columnNames = "state_number")})
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

    @Column(name = "vehicle_class")
    private String vehicleClass;

    @Column(name = "doors_count")
    private int doorsCount;

    @Column(name = "passengers_count")
    private int passengersCount;

    @Column(name = "is_air_conditioning")
    private boolean isAirConditioning;

    @Column(name = "is_audio")
    private boolean isAudio;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "fuel_tank_capacity")
    private int fuelTankCapacity;

    @Column(name = "engine_power")
    private int enginePower;

    @Column(name = "transmission_type")
    private String transmissionType;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "renting_start")
    private Date rentingStart;

    @Column(name = "renting_end")
    private Date rentingEnd;

    @Column(name = "day_price")
    private Double dayPrice;

    public Vehicle(String stateNumber, String vehicleModel, String vehicleClass,
                   int doorsCount, int passengersCount, boolean isAirConditioning, boolean isAudio,
                   String fuelType, int fuelTankCapacity, int enginePower, String transmissionType,
                   boolean isAvailable, Double dayPrice) {
        this.stateNumber = stateNumber;
        this.vehicleModel = vehicleModel;
        this.vehicleClass = vehicleClass;
        this.doorsCount = doorsCount;
        this.passengersCount = passengersCount;
        this.isAirConditioning = isAirConditioning;
        this.isAudio = isAudio;
        this.fuelType = fuelType;
        this.fuelTankCapacity = fuelTankCapacity;
        this.enginePower = enginePower;
        this.transmissionType = transmissionType;
        this.isAvailable = isAvailable;
        this.dayPrice = dayPrice;
    }
}
