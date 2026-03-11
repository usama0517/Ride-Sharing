package com.ridesharing.application.ride.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRideResponse {
    private UUID rideId;
    private double estimatedFare;
    private int estimatedArrivalMinutes;
    private String message;
}