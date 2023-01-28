package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.entity.authorization.ERole;
import com.arseeniy.mycarsharing.entity.authorization.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
