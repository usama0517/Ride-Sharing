package com.ridesharing.application.ride.command;

import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.enums.RideStatus;
import com.ridesharing.domain.enums.DriverStatus;
import com.ridesharing.domain.repository.RideRepository;
import com.ridesharing.domain.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartRideHandler {
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;


    public StartRideResponse handle(StartRideCommand command) {
        Ride ride = rideRepository.findById(command.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        // Verify ride is in ACCEPTED status
        if (ride.getStatus() != RideStatus.ACCEPTED) {
            return new StartRideResponse(
                    false,
                    "Ride cannot be started. Current status: " + ride.getStatus(),
                    ride.getStatus(),
                    null
            );
        }

        // Verify the correct driver is starting the ride
        if (!ride.getDriverId().equals(command.getDriverId())) {
            return new StartRideResponse(
                    false,
                    "Only the assigned driver can start this ride",
                    ride.getStatus(),
                    null
            );
        }

        // Update ride status to IN_PROGRESS
        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setStartedAt(LocalDateTime.now());

        // Update driver status
        Driver driver = driverRepository.findById(UUID.fromString(ride.getDriverId()))
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setStatus(DriverStatus.ON_RIDE);
        driverRepository.update(driver);

        rideRepository.update(ride);

        String formattedTime = ride.getStartedAt().format(DateTimeFormatter.ISO_LOCAL_TIME);

        return new StartRideResponse(
                true,
                "Ride started successfully",
                RideStatus.IN_PROGRESS,
                formattedTime
        );
    }
}