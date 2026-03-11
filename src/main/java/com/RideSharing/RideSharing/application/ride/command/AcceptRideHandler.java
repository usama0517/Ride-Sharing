package com.ridesharing.application.ride.command;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.enums.DriverStatus;
import com.ridesharing.domain.enums.RideStatus;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AcceptRideHandler {
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public AcceptRideResponse handle(AcceptRideCommand command) {
        Ride ride = rideRepository.findById(command.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.PENDING) {
            return new AcceptRideResponse(
                    false,
                    "Ride is no longer available",
                    ride.getStatus()
            );
        }

        Driver driver = driverRepository.findById(UUID.fromString(command.getDriverId()))
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (!driver.isAvailable()) {
            return new AcceptRideResponse(
                    false,
                    "Driver is not available",
                    ride.getStatus()
            );
        }

        // Accept the ride
        ride.setDriverId(command.getDriverId());
        ride.setStatus(RideStatus.ACCEPTED);
        ride.setAcceptedAt(LocalDateTime.now());

        // Update driver status
        driver.setAvailable(false);
        driver.setStatus(DriverStatus.ON_RIDE);

        rideRepository.update(ride);
        driverRepository.update(driver);

        return new AcceptRideResponse(
                true,
                "Ride accepted successfully",
                RideStatus.ACCEPTED
        );
    }
}