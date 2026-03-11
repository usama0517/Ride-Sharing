package com.ridesharing.application.ride.query;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.valueobject.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAvailableDriversHandler {
    private final DriverRepository driverRepository;

    public List<AvailableDriverDto> handle(GetAvailableDriversQuery query) {
        Location userLocation = new Location(
                query.getLatitude(),
                query.getLongitude(),
                "Current Location"
        );

        List<Driver> nearbyDrivers = driverRepository.findNearbyDrivers(
                userLocation,
                query.getRadiusKm()
        );

        // Filter by vehicle type if specified
        if (query.getVehicleType() != null) {
            nearbyDrivers = nearbyDrivers.stream()
                    .filter(d -> d.getVehicle().getType() == query.getVehicleType())
                    .collect(Collectors.toList());
        }

        // Calculate distance and sort by proximity
        List<AvailableDriverDto> result = nearbyDrivers.stream()
                .map(driver -> {
                    double distance = driver.getCurrentLocation().calculateDistance(userLocation);
                    int estimatedArrival = (int) Math.ceil(distance * 2); // Rough estimate: 2 min per km

                    return new AvailableDriverDto(
                            driver.getId(),
                            driver.getName(),
                            driver.getRating(),
                            driver.getVehicle().getModel(),
                            driver.getVehicle().getColor(),
                            driver.getVehicle().getLicensePlate(),
                            distance,
                            estimatedArrival
                    );
                })
                .sorted(Comparator.comparingDouble(AvailableDriverDto::getDistanceKm))
                .collect(Collectors.toList());

        // Apply limit if specified
        if (query.getLimit() != null && query.getLimit() > 0) {
            result = result.stream()
                    .limit(query.getLimit())
                    .collect(Collectors.toList());
        }

        return result;
    }
}