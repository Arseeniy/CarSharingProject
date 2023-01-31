package com.arseeniy.mycarsharing.common.entity.authorization;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users_library",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_name"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientIdSeq", sequenceName = "client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientIdSeq")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "birth_date")
    private String birthDate;

    @NotBlank
    @Size(max = 20)
    @Column(name = "user_name")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "passport_details")
    private String passportDetails;

    @Column(name = "current_vehicle")
    private String currentVehicle;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String birthDate, String username, String email,
                String password, String passportDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.username = username;
        this.email = email;
        this.password = password;
        this.passportDetails = passportDetails;
    }
}
