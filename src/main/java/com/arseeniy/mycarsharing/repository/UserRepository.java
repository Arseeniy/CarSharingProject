package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.common.entity.authorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

    Boolean existsByUsername(String userName);

    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_library SET current_vehicle = :stateNumber WHERE user_name = :userName", nativeQuery = true)
    void bookVehicle(String stateNumber, String userName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_library SET current_vehicle = :NULL WHERE user_name = :userName", nativeQuery = true)
    void closeRenting(String userName);

}
