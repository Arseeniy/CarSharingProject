package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAll();

    Vehicle findByStateNumber(String stateNumber);

    List<Vehicle> findAllByVehicleModel(String vehicleModel);

    void deleteAll();

    void deleteByStateNumber(String stateNumber);

    <S extends Vehicle> S save(S entity);
}
