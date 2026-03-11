package com.ridesharing.domain.entity;

import com.ridesharing.domain.enums.PaymentMethod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Passenger {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private PaymentMethod preferredPaymentMethod;
    private double rating;
    private int totalRides;
    private List<Ride> rideHistory = new ArrayList<>();

    public Passenger() {
        this.id = UUID.randomUUID();
    }
}