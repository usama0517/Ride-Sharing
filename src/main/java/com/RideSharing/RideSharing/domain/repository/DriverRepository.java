package com.ridesharing.domain.repository;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.enums.VehicleType;
import com.ridesharing.domain.valueobject.Location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepository {
    Optional<Driver> findById(UUID id);
    List<Driver> findAllAvailableDrivers(Location location, VehicleType vehicleType);
    Driver save(Driver driver);
    void update(Driver driver);
    List<Driver> findNearbyDrivers(Location location, double radiusKm);
    List<Driver> findTopRatedDrivers(int limit);
}