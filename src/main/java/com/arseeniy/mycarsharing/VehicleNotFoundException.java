package com.arseeniy.mycarsharing;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(String stateNumber) {
        super("Vehicle with the specified state number " + stateNumber + " does no exist");
    }
}
