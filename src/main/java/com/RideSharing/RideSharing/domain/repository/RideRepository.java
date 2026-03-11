package com.ridesharing.domain.repository;

import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.enums.RideStatus;
import com.ridesharing.domain.enums.RideType;
import com.ridesharing.domain.valueobject.Location;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RideRepository {
    Optional<Ride> findById(UUID id);
    List<Ride> findAllActiveRides();
    List<Ride> findRidesByPassengerId(String passengerId);
    List<Ride> findRidesByDriverId(String driverId);
    Ride save(Ride ride);
    void update(Ride ride);
    List<Ride> findAvailableRides(Location location, RideType rideType);
    List<Ride> findRidesByStatus(RideStatus status);
    List<Ride> findCompletedRidesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}