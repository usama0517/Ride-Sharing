package com.ridesharing.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;
    private String address;

    public double calculateDistance(Location other) {
        // Simplified distance calculation (Haversine formula would be used in real app)
        double latDiff = Math.abs(this.latitude - other.latitude);
        double lonDiff = Math.abs(this.longitude - other.longitude);
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111; // Approx km
    }
}