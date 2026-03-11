package com.ridesharing.application.ride.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideHistoryDto {
    private UUID rideId;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String pickupAddress;
    private String dropoffAddress;
    private String status;
    private Double actualFare;
    private String rideType;
    private Double distance;
    private String otherPartyName;
    private double otherPartyRating;
}