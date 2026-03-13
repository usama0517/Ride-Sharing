package com.ridesharing.api.controller;

import com.ridesharing.api.dto.PassengerDTO;
import com.ridesharing.domain.entity.Passenger;
import com.ridesharing.domain.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerRepository passengerRepository;

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        List<PassengerDTO> passengerDTOs = passengers.stream()
                .map(PassengerDTO::fromPassenger)
                .collect(Collectors.toList());
        return ResponseEntity.ok(passengerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable UUID id) {
        return passengerRepository.findById(id)
                .map(PassengerDTO::fromPassenger)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PassengerDTO> getPassengerByEmail(@PathVariable String email) {
        return passengerRepository.findByEmail(email)
                .map(PassengerDTO::fromPassenger)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}