package com.arseeniy.mycarsharing.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "client_library")
public class Client {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientIdSeq", sequenceName = "client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientIdSeq")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Size(min = 4, message = "Не меньше 4 знаков")
    @Column(name = "user_name")
    private String userName;

    @Size(min = 10, message = "Не меньше 10 знаков")
    @Column(name = "password")
    private String password;

    @Column(name = "current_vehicle")
    private String currentVehicle;

}
