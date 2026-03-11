package com.ridesharing.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideResponse {
    private UUID rideId;
    private String passengerName;
    private String driverName;
    private String pickupAddress;
    private String dropoffAddress;
    private String status;
    private LocalDateTime requestedAt;
    private double estimatedFare;
    private String rideType;
}