package com.ridesharing.infrastructure.persistence;

import com.ridesharing.domain.entity.Ride;
import com.ridesharing.domain.enums.RideStatus;
import com.ridesharing.domain.enums.RideType;
import com.ridesharing.domain.repository.RideRepository;
import com.ridesharing.domain.valueobject.Location;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryRideRepository implements RideRepository {
    private final Map<UUID, Ride> rides = new ConcurrentHashMap<>();

    @Override
    public Optional<Ride> findById(UUID id) {
        return Optional.ofNullable(rides.get(id));
    }

    @Override
    public List<Ride> findAllActiveRides() {
        return rides.values().stream()
                .filter(r -> r.getStatus() == RideStatus.PENDING ||
                        r.getStatus() == RideStatus.ACCEPTED ||
                        r.getStatus() == RideStatus.IN_PROGRESS)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ride> findRidesByPassengerId(String passengerId) {
        return rides.values().stream()
                .filter(r -> r.getPassengerId().equals(passengerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ride> findRidesByDriverId(String driverId) {
        return rides.values().stream()
                .filter(r -> driverId.equals(r.getDriverId()))
                .collect(Collectors.toList());
    }

    @Override
    public Ride save(Ride ride) {
        rides.put(ride.getId(), ride);
        return ride;
    }

    @Override
    public void update(Ride ride) {
        rides.put(ride.getId(), ride);
    }

    @Override
    public List<Ride> findAvailableRides(Location location, RideType rideType) {
        return rides.values().stream()
                .filter(r -> r.getStatus() == RideStatus.PENDING &&
                        r.getRideType() == rideType)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ride> findRidesByStatus(RideStatus status) {
        return rides.values().stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ride> findCompletedRidesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return rides.values().stream()
                .filter(r -> r.getStatus() == RideStatus.COMPLETED)
                .filter(r -> r.getCompletedAt() != null)
                .filter(r -> !r.getCompletedAt().isBefore(startDate) &&
                        !r.getCompletedAt().isAfter(endDate))
                .collect(Collectors.toList());
    }
}