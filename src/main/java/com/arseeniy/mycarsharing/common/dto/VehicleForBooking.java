package com.arseeniy.mycarsharing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class VehicleForBooking {

    private String stateNumber;

    private String vehicleModel;

    private int vehicleRunningDistance;

    private Double minutePrice;

    private Double hourPrice;

    private Double dayPrice;

}
