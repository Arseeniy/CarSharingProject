package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.entity.booking.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAll();

    Vehicle findByStateNumber(String stateNumber);

    List<Vehicle> findAllByVehicleModel(String vehicleModel);

    void deleteAll();

    void deleteByStateNumber(String stateNumber);

    <S extends Vehicle> S save(S entity);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle_library SET status = :true WHERE stateNumber = :stateNumber", nativeQuery = true)
    void bookVehicle(String stateNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle_library SET status = :false WHERE stateNumber = :stateNumber", nativeQuery = true)
    void closeRenting(String stateNumber);
}
