package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.entity.booking.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    public Client findByUserName(String userName);

    @Modifying
    @Transactional
//    @Query("Update Client user set user.currentVehicle = :stateNumber where user.userName = :userName")
    @Query(value = "UPDATE client_library SET current_vehicle = :stateNumber WHERE user_name = :userName", nativeQuery = true)
    void bookVehicle(String stateNumber, String userName);
    @Modifying
    @Transactional
    @Query(value = "UPDATE client_library SET current_vehicle = :null WHERE user_name = :userName", nativeQuery = true)
    void closeRenting(String userName);

}
