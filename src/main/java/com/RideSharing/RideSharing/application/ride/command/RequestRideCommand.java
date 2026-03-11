package com.ridesharing.application.ride.command;

import com.ridesharing.domain.enums.RideType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRideCommand {
    private String passengerId;
    private double pickupLatitude;
    private double pickupLongitude;
    private String pickupAddress;
    private double dropoffLatitude;
    private double dropoffLongitude;
    private String dropoffAddress;
    private RideType rideType;
}