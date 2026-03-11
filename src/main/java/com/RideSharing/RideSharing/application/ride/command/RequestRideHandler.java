package com.ridesharing.application.ride.command;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.entity.Passenger;
import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.repository.PassengerRepository;
import com.ridesharing.domain.repository.RideRepository;
import com.ridesharing.domain.valueobject.Location;
import com.ridesharing.application.ride.factory.RideFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestRideHandler {
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;
    private final RideFactory rideFactory;

    public RequestRideResponse handle(RequestRideCommand command) {
        // Validate passenger exists
        Passenger passenger = passengerRepository.findById(UUID.fromString(command.getPassengerId()))
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        // Create locations
        Location pickup = new Location(
                command.getPickupLatitude(),
                command.getPickupLongitude(),
                command.getPickupAddress()
        );
        Location dropoff = new Location(
                command.getDropoffLatitude(),
                command.getDropoffLongitude(),
                command.getDropoffAddress()
        );

        // Find nearby available drivers
        List<Driver> nearbyDrivers = driverRepository.findNearbyDrivers(pickup, 5.0);

        if (nearbyDrivers.isEmpty()) {
            throw new RuntimeException("No drivers available nearby");
        }

        // Calculate estimated fare using factory
        double estimatedFare = rideFactory.calculateEstimatedFare(pickup, dropoff, command.getRideType());

        // Create ride using factory
        Ride ride = rideFactory.createRide(
                command.getPassengerId(),
                pickup,
                dropoff,
                command.getRideType(),
                estimatedFare
        );

        // Save ride
        rideRepository.save(ride);

        // Calculate estimated arrival (simplified)
        int estimatedArrivalMinutes = 3 + (int)(Math.random() * 7);

        return new RequestRideResponse(
                ride.getId(),
                estimatedFare,
                estimatedArrivalMinutes,
                "Ride requested successfully. Looking for drivers..."
        );
    }
}