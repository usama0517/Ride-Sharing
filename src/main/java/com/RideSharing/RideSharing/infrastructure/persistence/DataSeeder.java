package com.ridesharing.infrastructure.persistence;

import com.ridesharing.domain.entity.Driver;
import com.ridesharing.domain.entity.Passenger;
import com.ridesharing.domain.enums.DriverStatus;
import com.ridesharing.domain.enums.PaymentMethod;
import com.ridesharing.domain.enums.VehicleType;
import com.ridesharing.domain.repository.DriverRepository;
import com.ridesharing.domain.repository.PassengerRepository;
import com.ridesharing.domain.valueobject.Location;
import com.ridesharing.domain.valueobject.Vehicle;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    @PostConstruct
    public void seedData() {
        seedDrivers();
        seedPassengers();
    }

    private void seedDrivers() {
        // Driver 1 - Economy
        Driver driver1 = new Driver();
        driver1.setName("John Smith");
        driver1.setEmail("john.smith@example.com");
        driver1.setPhone("+1234567890");
        driver1.setCurrentLocation(new Location(40.7128, -74.0060, "Downtown NYC"));
        driver1.setStatus(DriverStatus.ONLINE);
        driver1.setAvailable(true);
        driver1.setRating(4.8);
        driver1.setTotalRides(1250);
        driver1.setVehicle(new Vehicle("ABC123", "Toyota Corolla", "Silver", 2022, VehicleType.ECONOMY));
        driverRepository.save(driver1);

        // Driver 2 - Comfort
        Driver driver2 = new Driver();
        driver2.setName("Sarah Johnson");
        driver2.setEmail("sarah.j@example.com");
        driver2.setPhone("+1234567891");
        driver2.setCurrentLocation(new Location(40.7580, -73.9855, "Times Square"));
        driver2.setStatus(DriverStatus.ONLINE);
        driver2.setAvailable(true);
        driver2.setRating(4.9);
        driver2.setTotalRides(850);
        driver2.setVehicle(new Vehicle("XYZ789", "Honda Accord", "Black", 2023, VehicleType.COMFORT));
        driverRepository.save(driver2);

        // Driver 3 - Luxury
        Driver driver3 = new Driver();
        driver3.setName("Michael Chen");
        driver3.setEmail("michael.c@example.com");
        driver3.setPhone("+1234567892");
        driver3.setCurrentLocation(new Location(40.7549, -73.9840, "Bryant Park"));
        driver3.setStatus(DriverStatus.ONLINE);
        driver3.setAvailable(true);
        driver3.setRating(5.0);
        driver3.setTotalRides(320);
        driver3.setVehicle(new Vehicle("LUX001", "Tesla Model S", "White", 2024, VehicleType.LUXURY));
        driverRepository.save(driver3);

        // Driver 4 - XL
        Driver driver4 = new Driver();
        driver4.setName("Emily Davis");
        driver4.setEmail("emily.d@example.com");
        driver4.setPhone("+1234567893");
        driver4.setCurrentLocation(new Location(40.7614, -73.9776, "Rockefeller Center"));
        driver4.setStatus(DriverStatus.ONLINE);
        driver4.setAvailable(true);
        driver4.setRating(4.7);
        driver4.setTotalRides(2100);
        driver4.setVehicle(new Vehicle("XL555", "Ford Explorer", "Blue", 2021, VehicleType.XL));
        driverRepository.save(driver4);
    }

    private void seedPassengers() {
        // Passenger 1
        Passenger passenger1 = new Passenger();
        passenger1.setName("Alice Wonderland");
        passenger1.setEmail("alice.w@example.com");
        passenger1.setPhone("+1987654321");
        passenger1.setPreferredPaymentMethod(PaymentMethod.CREDIT_CARD);
        passenger1.setRating(4.9);
        passenger1.setTotalRides(45);
        passengerRepository.save(passenger1);

        // Passenger 2
        Passenger passenger2 = new Passenger();
        passenger2.setName("Bob Builder");
        passenger2.setEmail("bob.b@example.com");
        passenger2.setPhone("+1987654322");
        passenger2.setPreferredPaymentMethod(PaymentMethod.DIGITAL_WALLET);
        passenger2.setRating(4.8);
        passenger2.setTotalRides(23);
        passengerRepository.save(passenger2);

        // Passenger 3
        Passenger passenger3 = new Passenger();
        passenger3.setName("Charlie Brown");
        passenger3.setEmail("charlie.b@example.com");
        passenger3.setPhone("+1987654323");
        passenger3.setPreferredPaymentMethod(PaymentMethod.CASH);
        passenger3.setRating(5.0);
        passenger3.setTotalRides(67);
        passengerRepository.save(passenger3);
    }
}