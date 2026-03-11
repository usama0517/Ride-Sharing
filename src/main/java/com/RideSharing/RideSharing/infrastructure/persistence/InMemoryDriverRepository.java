package com.ridesharing.infrastructure.persistence;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.enums.VehicleType;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.valueobject.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryDriverRepository implements DriverRepository {
    private final Map<UUID, Driver> drivers = new ConcurrentHashMap<>();

    @Override
    public Optional<Driver> findById(UUID id) {
        return Optional.ofNullable(drivers.get(id));
    }

    @Override
    public List<Driver> findAllAvailableDrivers(Location location, VehicleType vehicleType) {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .filter(d -> vehicleType == null || d.getVehicle().getType() == vehicleType)
                .collect(Collectors.toList());
    }

    @Override
    public Driver save(Driver driver) {
        drivers.put(driver.getId(), driver);
        return driver;
    }

    @Override
    public void update(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    @Override
    public List<Driver> findNearbyDrivers(Location location, double radiusKm) {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .filter(d -> d.getCurrentLocation().calculateDistance(location) <= radiusKm)
                .collect(Collectors.toList());
    }

    @Override
    public List<Driver> findTopRatedDrivers(int limit) {
        return drivers.values().stream()
                .sorted((d1, d2) -> Double.compare(d2.getRating(), d1.getRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}