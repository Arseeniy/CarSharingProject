package com.arseeniy.mycarsharing.repository;

import com.arseeniy.mycarsharing.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    public Client findByUserName(String userName);
}
