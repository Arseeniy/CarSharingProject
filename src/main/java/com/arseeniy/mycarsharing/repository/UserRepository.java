package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.common.entity.authorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_library SET current_vehicle = :stateNumber WHERE user_name = :username", nativeQuery = true)
    void bookVehicle(@Param("stateNumber") String stateNumber, @Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_library SET current_vehicle = NULL WHERE user_name = :userName", nativeQuery = true)
    void closeRenting(@Param("userName") String userName);

}
