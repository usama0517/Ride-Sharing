package com.ridesharing.application.ride.factory;

import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.enums.RideType;
import com.ridesharing.domain.valueobject.Location;
import org.springframework.stereotype.Component;

@Component
public class RideFactory {
    private static final double BASE_FARE = 50.0;
    private static final double PER_KM_RATE = 15.0;
    private static final double PER_MINUTE_RATE = 2.0;

    public Ride createRide(String passengerId, Location pickup, Location dropoff,
                           RideType rideType, double estimatedFare) {
        Ride ride = new Ride();
        ride.setPassengerId(passengerId);
        ride.setPickupLocation(pickup);
        ride.setDropoffLocation(dropoff);
        ride.setRideType(rideType);
        ride.setEstimatedFare(estimatedFare);
        return ride;
    }

    public double calculateEstimatedFare(Location pickup, Location dropoff, RideType rideType) {
        double distance = pickup.calculateDistance(dropoff);
        double estimatedDuration = distance * 2; // Rough estimate: 2 minutes per km

        double baseAmount = BASE_FARE +
                (PER_KM_RATE * distance) +
                (PER_MINUTE_RATE * estimatedDuration);

        // Apply multiplier based on ride type
        switch (rideType) {
            case POOL:
                return baseAmount * 0.7; // 30% discount
            case PREMIUM:
                return baseAmount * 1.5; // 50% premium
            case STANDARD:
            default:
                return baseAmount;
        }
    }
}