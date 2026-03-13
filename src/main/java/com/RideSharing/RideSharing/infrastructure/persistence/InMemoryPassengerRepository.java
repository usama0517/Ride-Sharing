package com.ridesharing.infrastructure.persistence;

import com.ridesharing.domain.entity.Passenger;
import com.ridesharing.domain.repository.PassengerRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPassengerRepository implements PassengerRepository {
    private final Map<UUID, Passenger> passengers = new ConcurrentHashMap<>();

    @Override
    public Optional<Passenger> findById(UUID id) {
        return Optional.ofNullable(passengers.get(id));
    }

    @Override
    public Optional<Passenger> findByEmail(String email) {
        return passengers.values().stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Passenger save(Passenger passenger) {
        passengers.put(passenger.getId(), passenger);
        return passenger;
    }

    @Override
    public void update(Passenger passenger) {
        passengers.put(passenger.getId(), passenger);
    }

    @Override
    public List<Passenger> findAll() {  // ADD THIS METHOD
        return new ArrayList<>(passengers.values());
    }
}
