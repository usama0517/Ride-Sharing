package com.ridesharing.domain.entity;

import com.ridesharing.domain.enums.DriverStatus;
import com.ridesharing.domain.valueobject.Location;
import com.ridesharing.domain.valueobject.Vehicle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Driver {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private Location currentLocation;
    private DriverStatus status;
    private Vehicle vehicle;
    private double rating;
    private int totalRides;
    private boolean isAvailable;
    private List<Ride> rideHistory = new ArrayList<>();

    public Driver() {
        this.id = UUID.randomUUID();
    }
}