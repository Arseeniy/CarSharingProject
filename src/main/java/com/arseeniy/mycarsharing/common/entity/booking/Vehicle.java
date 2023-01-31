package com.arseeniy.mycarsharing.common.entity.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "fuel_amount")
    private int fuelAmount;

    @Column(name = "distance_for_vehicle")
    private int distanceForVehicle;

    @Column(name = "status")
    private boolean status;

    public Vehicle(String stateNumber, String vehicleModel, int fuelAmount, int distanceForVehicle) {
        this.stateNumber = stateNumber;
        this.vehicleModel = vehicleModel;
        this.fuelAmount = fuelAmount;
        this.distanceForVehicle = distanceForVehicle;
    }
}
