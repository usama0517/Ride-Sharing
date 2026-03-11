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
public class CompleteRideHandler {
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public CompleteRideResponse handle(CompleteRideCommand command) {
        Ride ride = rideRepository.findById(command.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.IN_PROGRESS) {
            return new CompleteRideResponse(
                    false,
                    "Ride cannot be completed. Current status: " + ride.getStatus(),
                    0,
                    null
            );
        }

        // Complete the ride
        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());
        ride.setDistance(command.getActualDistance());
        ride.setDuration(command.getActualDuration());
        ride.setActualFare(command.getFinalFare());

        // Update driver availability
        if (ride.getDriverId() != null) {
            Driver driver = driverRepository.findById(UUID.fromString(ride.getDriverId()))
                    .orElse(null);
            if (driver != null) {
                driver.setAvailable(true);
                driver.setStatus(DriverStatus.ONLINE);
                driver.setTotalRides(driver.getTotalRides() + 1);
                driverRepository.update(driver);
            }
        }

        rideRepository.update(ride);

        // Generate receipt URL (simplified)
        String receiptUrl = "/receipts/" + ride.getId().toString();

        return new CompleteRideResponse(
                true,
                "Ride completed successfully",
                command.getFinalFare(),
                receiptUrl
        );
    }
}