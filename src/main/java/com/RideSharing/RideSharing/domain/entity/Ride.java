package com.ridesharing.domain.entity;

import com.ridesharing.domain.enums.RideStatus;
import com.ridesharing.domain.enums.RideType;
import com.ridesharing.domain.valueobject.Location;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Ride {
    private UUID id;
    private String passengerId;
    private String driverId;
    private Location pickupLocation;
    private Location dropoffLocation;
    private LocalDateTime requestedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private RideStatus status;
    private RideType rideType;
    private double estimatedFare;
    private Double actualFare;
    private Double distance;
    private Integer duration;

    public Ride() {
        this.id = UUID.randomUUID();
        this.requestedAt = LocalDateTime.now();
        this.status = RideStatus.PENDING;
    }
}