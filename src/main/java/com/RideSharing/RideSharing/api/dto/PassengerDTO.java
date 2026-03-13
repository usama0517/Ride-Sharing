package com.ridesharing.api.dto;

import com.ridesharing.domain.entity.Passenger;
import java.util.UUID;

public class PassengerDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String preferredPaymentMethod;
    private double rating;
    private int totalRides;

    // Default constructor
    public PassengerDTO() {
    }

    // Constructor with fields
    public PassengerDTO(UUID id, String name, String email, String phone,
                        String preferredPaymentMethod, double rating, int totalRides) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.rating = rating;
        this.totalRides = totalRides;
    }

    // Static factory method to create DTO from Passenger entity
    public static PassengerDTO fromPassenger(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(passenger.getId());
        dto.setName(passenger.getName());
        dto.setEmail(passenger.getEmail());
        dto.setPhone(passenger.getPhone());
        dto.setPreferredPaymentMethod(passenger.getPreferredPaymentMethod().toString());
        dto.setRating(passenger.getRating());
        dto.setTotalRides(passenger.getTotalRides());
        return dto;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(int totalRides) {
        this.totalRides = totalRides;
    }
}