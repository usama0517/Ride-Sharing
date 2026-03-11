package com.ridesharing.application.ride.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDriverDto {
    private UUID driverId;
    private String name;
    private double rating;
    private String vehicleModel;
    private String vehicleColor;
    private String licensePlate;
    private double distanceKm;
    private int estimatedArrivalMinutes;
}