package com.ridesharing.application.ride.query;

import com.ridesharing.domain.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAvailableDriversQuery {
    private double latitude;
    private double longitude;
    private double radiusKm;
    private VehicleType vehicleType;
    private Integer limit;
}