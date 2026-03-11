package com.ridesharing.application.ride.query;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.enums.RideStatus;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetDriverEarningsHandler {
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public DriverEarningsDto handle(GetDriverEarningsQuery query) {
        Driver driver = driverRepository.findById(UUID.fromString(query.getDriverId()))
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        List<Ride> completedRides = rideRepository.findRidesByDriverId(query.getDriverId())
                .stream()
                .filter(ride -> ride.getStatus() == RideStatus.COMPLETED)
                .filter(ride -> ride.getCompletedAt() != null)
                .filter(ride -> !ride.getCompletedAt().isBefore(query.getStartDate()))
                .filter(ride -> !ride.getCompletedAt().isAfter(query.getEndDate()))
                .toList();

        BigDecimal totalEarnings = BigDecimal.ZERO;
        BigDecimal cashEarnings = BigDecimal.ZERO;
        BigDecimal cardEarnings = BigDecimal.ZERO;
        BigDecimal digitalWalletEarnings = BigDecimal.ZERO;

        for (Ride ride : completedRides) {
            if (ride.getActualFare() != null) {
                BigDecimal fare = BigDecimal.valueOf(ride.getActualFare());
                totalEarnings = totalEarnings.add(fare);

                // In a real app, you'd get payment method from ride/passenger
                // This is simplified for demonstration
                if (Math.random() > 0.5) {
                    cashEarnings = cashEarnings.add(fare);
                } else {
                    cardEarnings = cardEarnings.add(fare);
                }
            }
        }

        return new DriverEarningsDto(
                totalEarnings,
                cashEarnings,
                cardEarnings,
                digitalWalletEarnings,
                completedRides.size(),
                driver.getRating(),
                query.getStartDate(),
                query.getEndDate()
        );
    }
}