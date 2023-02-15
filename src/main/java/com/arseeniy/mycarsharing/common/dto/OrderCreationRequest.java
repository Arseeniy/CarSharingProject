package com.arseeniy.mycarsharing.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrderCreationRequest {

    @NotBlank
    private String stateNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    private String passportNumber;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate passportIssueDate;

    @NotBlank
    private String issuingAuthority;

    @NotBlank
    private String registrationPlace;

    @NotBlank
    private String driverLicenseNumber;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate driverLicenseIssueDate;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate rentingStart;

    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate rentingEnd;


}
