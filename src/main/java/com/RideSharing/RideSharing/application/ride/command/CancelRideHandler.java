package com.ridesharing.application.ride.command;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.enums.DriverStatus;
import com.ridesharing.domain.enums.RideStatus;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CancelRideHandler {
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public CancelRideResponse handle(CancelRideCommand command) {
        Ride ride = rideRepository.findById(command.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        // Check if ride can be cancelled
        if (!canBeCancelled(ride)) {
            return new CancelRideResponse(
                    false,
                    "Ride cannot be cancelled at this stage",
                    BigDecimal.ZERO,
                    null
            );
        }

        BigDecimal cancellationFee = calculateCancellationFee(ride, command.getCancelledBy());
        String refundStatus = determineRefundStatus(ride, command.getCancelledBy());

        // Update ride status
        if ("PASSENGER".equals(command.getCancelledBy())) {
            ride.setStatus(RideStatus.CANCELLED_BY_PASSENGER);
        } else {
            ride.setStatus(RideStatus.CANCELLED_BY_DRIVER);
        }

        // Free up the driver if one was assigned
        if (ride.getDriverId() != null) {
            Driver driver = driverRepository.findById(UUID.fromString(ride.getDriverId()))
                    .orElse(null);
            if (driver != null) {
                driver.setAvailable(true);
                driver.setStatus(DriverStatus.ONLINE);
                driverRepository.update(driver);
            }
        }

        rideRepository.update(ride);

        return new CancelRideResponse(
                true,
                "Ride cancelled successfully",
                cancellationFee,
                refundStatus
        );
    }

    private boolean canBeCancelled(Ride ride) {
        return ride.getStatus() == RideStatus.PENDING ||
                ride.getStatus() == RideStatus.ACCEPTED;
    }

    private BigDecimal calculateCancellationFee(Ride ride, String cancelledBy) {
        // No fee if cancelled by driver
        if ("DRIVER".equals(cancelledBy)) {
            return BigDecimal.ZERO;
        }

        // Calculate fee based on time elapsed since booking
        Duration timeSinceBooking = Duration.between(ride.getRequestedAt(), LocalDateTime.now());

        // Free cancellation within first 2 minutes
        if (timeSinceBooking.toMinutes() < 2) {
            return BigDecimal.ZERO;
        }

        // $5 fee after 2 minutes
        return new BigDecimal("5.00");
    }

    private String determineRefundStatus(Ride ride, String cancelledBy) {
        if ("DRIVER".equals(cancelledBy)) {
            return "FULL_REFUND";
        }

        Duration timeSinceBooking = Duration.between(ride.getRequestedAt(), LocalDateTime.now());

        if (timeSinceBooking.toMinutes() < 2) {
            return "FULL_REFUND";
        } else {
            return "PARTIAL_REFUND";
        }
    }
}