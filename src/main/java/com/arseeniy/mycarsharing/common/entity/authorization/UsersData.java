package com.arseeniy.mycarsharing.common.entity.authorization;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "users_data_library")
public class UsersData {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientIdSeq", sequenceName = "client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientIdSeq")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_issue_date")
    private LocalDate passportIssueDate;

    @Column(name = "issuing_authority")
    private String issuingAuthority;

    @Column(name = "registration_place")
    private String registrationPlace;

    @Column(name = "driver_license_number")
    private String driverLicenseNumber;

    @Column(name = "dl_issue_date")
    private LocalDate driverLicenseIssueDate;

    public UsersData(String firstName, String lastName, LocalDate birthDate, String passportNumber,
                     LocalDate passportIssueDate, String issuingAuthority, String registrationPlace,
                     String driverLicenseNumber, LocalDate driverLicenseIssueDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passportNumber = passportNumber;
        this.passportIssueDate = passportIssueDate;
        this.issuingAuthority = issuingAuthority;
        this.registrationPlace = registrationPlace;
        this.driverLicenseNumber = driverLicenseNumber;
        this.driverLicenseIssueDate = driverLicenseIssueDate;
    }
}
