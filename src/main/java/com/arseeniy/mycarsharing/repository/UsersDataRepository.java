package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.common.entity.authorization.UsersData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDataRepository extends JpaRepository<UsersData, Long> {
}
