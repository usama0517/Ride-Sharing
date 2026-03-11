package com.ridesharing.application.ride.query;

import com.ridesharing.application.common.dto.RideResponse;
import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.entity.Passenger;
import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.repository.PassengerRepository;
import com.ridesharing.domain.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetActiveRidesHandler {
    private final RideRepository rideRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    public List<RideResponse> handle(GetActiveRidesQuery query) {
        List<Ride> activeRides = rideRepository.findAllActiveRides();

        // Apply status filter if provided
        if (query.getStatus() != null && !query.getStatus().isEmpty()) {
            activeRides = activeRides.stream()
                    .filter(ride -> ride.getStatus().name().equalsIgnoreCase(query.getStatus()))
                    .collect(Collectors.toList());
        }

        return activeRides.stream()
                .filter(ride -> {
                    // Apply driver filter
                    if (query.getDriverId() != null && !query.getDriverId().isEmpty()) {
                        return query.getDriverId().equals(ride.getDriverId());
                    }
                    // Apply passenger filter
                    if (query.getPassengerId() != null && !query.getPassengerId().isEmpty()) {
                        return query.getPassengerId().equals(ride.getPassengerId());
                    }
                    return true;
                })
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private RideResponse mapToDto(Ride ride) {
        RideResponse dto = new RideResponse();
        dto.setRideId(ride.getId());
        dto.setPickupAddress(ride.getPickupLocation().getAddress());
        dto.setDropoffAddress(ride.getDropoffLocation().getAddress());
        dto.setStatus(ride.getStatus().name());
        dto.setRequestedAt(ride.getRequestedAt());
        dto.setEstimatedFare(ride.getEstimatedFare());
        dto.setRideType(ride.getRideType().name());

        // Get passenger name
        passengerRepository.findById(UUID.fromString(ride.getPassengerId()))
                .ifPresent(p -> dto.setPassengerName(p.getName()));

        // Get driver name if assigned
        if (ride.getDriverId() != null) {
            driverRepository.findById(UUID.fromString(ride.getDriverId()))
                    .ifPresent(d -> dto.setDriverName(d.getName()));
        }

        return dto;
    }
}