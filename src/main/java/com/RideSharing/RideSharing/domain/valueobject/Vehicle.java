package com.ridesharing.domain.valueobject;

import com.ridesharing.domain.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vehicle {
    private String licensePlate;
    private String model;
    private String color;
    private int year;
    private VehicleType type;
}