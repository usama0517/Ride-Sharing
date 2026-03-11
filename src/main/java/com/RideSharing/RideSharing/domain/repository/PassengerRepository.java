package com.ridesharing.domain.repository;

import com.ridesharing.domain.entity.Passenger;

import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository {
    Optional<Passenger> findById(UUID id);
    Optional<Passenger> findByEmail(String email);
    Passenger save(Passenger passenger);
    void update(Passenger passenger);
}