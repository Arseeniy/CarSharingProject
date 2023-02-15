package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByStateNumber(String stateNumber);

    List<Vehicle> findAllByVehicleModel(String vehicleModel);

    Boolean existsByStateNumber(String stateNumber);

    void deleteByStateNumber(String stateNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle_library SET is_available = false, renting_start = :rentingStart, renting_end = :rentingEnd WHERE state_number = :stateNumber", nativeQuery = true)
    void bookVehicle(@Param("stateNumber") String stateNumber, @Param("rentingStart") LocalDate rentingStart,
                     @Param("rentingEnd") LocalDate rentingEnd);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle_library SET is_available = true, renting_start = NULL, renting_end = NULL WHERE state_number = :stateNumber", nativeQuery = true)
    void closeVehicleRenting(@Param("stateNumber") String stateNumber);
}
