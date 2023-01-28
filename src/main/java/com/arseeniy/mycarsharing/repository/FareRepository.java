package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.entity.booking.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare, Long> {

    Fare findByVehicleModel(String vehicleModel);

    <S extends Fare> S save(S entity);
}
