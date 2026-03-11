package com.ridesharing.application.ride.query;

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
public class GetRideHistoryHandler {
    private final RideRepository rideRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    public List<RideHistoryDto> handle(GetRideHistoryQuery query) {
        List<Ride> rides;

        if (query.getUserType() == GetRideHistoryQuery.UserType.PASSENGER) {
            rides = rideRepository.findRidesByPassengerId(query.getUserId());
        } else {
            rides = rideRepository.findRidesByDriverId(query.getUserId());
        }

        // Apply date filters
        if (query.getFromDate() != null) {
            rides = rides.stream()
                    .filter(r -> !r.getRequestedAt().isBefore(query.getFromDate()))
                    .collect(Collectors.toList());
        }

        if (query.getToDate() != null) {
            rides = rides.stream()
                    .filter(r -> !r.getRequestedAt().isAfter(query.getToDate()))
                    .collect(Collectors.toList());
        }

        // Sort by most recent
        rides.sort((r1, r2) -> r2.getRequestedAt().compareTo(r1.getRequestedAt()));

        // Apply limit
        if (query.getLimit() != null && query.getLimit() > 0) {
            rides = rides.stream()
                    .limit(query.getLimit())
                    .collect(Collectors.toList());
        }

        return rides.stream()
                .map(ride -> mapToHistoryDto(ride, query.getUserType()))
                .collect(Collectors.toList());
    }

    private RideHistoryDto mapToHistoryDto(Ride ride, GetRideHistoryQuery.UserType userType) {
        RideHistoryDto dto = new RideHistoryDto();
        dto.setRideId(ride.getId());
        dto.setRequestedAt(ride.getRequestedAt());
        dto.setCompletedAt(ride.getCompletedAt());
        dto.setPickupAddress(ride.getPickupLocation().getAddress());
        dto.setDropoffAddress(ride.getDropoffLocation().getAddress());
        dto.setStatus(ride.getStatus().name());
        dto.setActualFare(ride.getActualFare());
        dto.setRideType(ride.getRideType().name());
        dto.setDistance(ride.getDistance());

        if (userType == GetRideHistoryQuery.UserType.PASSENGER) {
            // For passenger view, show driver info
            if (ride.getDriverId() != null) {
                driverRepository.findById(UUID.fromString(ride.getDriverId()))
                        .ifPresent(driver -> {
                            dto.setOtherPartyName(driver.getName());
                            dto.setOtherPartyRating(driver.getRating());
                        });
            } else {
                dto.setOtherPartyName("Not Assigned");
            }
        } else {
            // For driver view, show passenger info
            passengerRepository.findById(UUID.fromString(ride.getPassengerId()))
                    .ifPresent(passenger -> {
                        dto.setOtherPartyName(passenger.getName());
                        dto.setOtherPartyRating(passenger.getRating());
                    });
        }

        return dto;
    }
}