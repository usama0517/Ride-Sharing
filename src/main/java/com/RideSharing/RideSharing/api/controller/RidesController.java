package com.ridesharing.api.controller;

import com.ridesharing.application.common.dto.RideResponse;
import com.ridesharing.application.ride.command.*;
import com.ridesharing.application.ride.query.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RidesController {

    private final RequestRideHandler requestRideHandler;
    private final AcceptRideHandler acceptRideHandler;
    private final CompleteRideHandler completeRideHandler;
    private final CancelRideHandler cancelRideHandler;
    private final GetActiveRidesHandler getActiveRidesHandler;
    private final GetRideHistoryHandler getRideHistoryHandler;
    private final GetAvailableDriversHandler getAvailableDriversHandler;
    private final GetDriverEarningsHandler getDriverEarningsHandler;
    private final StartRideHandler startRideHandler;
    // ============= COMMAND ENDPOINTS (Write Operations) =============

    @PostMapping("/request")
    public ResponseEntity<RequestRideResponse> requestRide(@RequestBody RequestRideCommand command) {
        RequestRideResponse response = requestRideHandler.handle(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{rideId}/accept")
    public ResponseEntity<AcceptRideResponse> acceptRide(
            @PathVariable UUID rideId,
            @RequestBody AcceptRideCommand command) {
        command.setRideId(rideId);
        AcceptRideResponse response = acceptRideHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{rideId}/complete")
    public ResponseEntity<CompleteRideResponse> completeRide(
            @PathVariable UUID rideId,
            @RequestBody CompleteRideCommand command) {
        command.setRideId(rideId);
        CompleteRideResponse response = completeRideHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<CancelRideResponse> cancelRide(
            @PathVariable UUID rideId,
            @RequestBody CancelRideCommand command) {
        command.setRideId(rideId);
        CancelRideResponse response = cancelRideHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    // ============= QUERY ENDPOINTS (Read Operations) =============

    @GetMapping("/active")
    public ResponseEntity<List<RideResponse>> getActiveRides(
            @RequestParam(required = false) String driverId,
            @RequestParam(required = false) String passengerId,
            @RequestParam(required = false) String status) {

        GetActiveRidesQuery query = new GetActiveRidesQuery(driverId, passengerId, status);
        List<RideResponse> rides = getActiveRidesHandler.handle(query);
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/history")
    public ResponseEntity<List<RideHistoryDto>> getRideHistory(
            @RequestParam String userId,
            @RequestParam GetRideHistoryQuery.UserType userType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(required = false) Integer limit) {

        GetRideHistoryQuery query = new GetRideHistoryQuery(userId, userType, fromDate, toDate, limit);
        List<RideHistoryDto> history = getRideHistoryHandler.handle(query);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/available-drivers")
    public ResponseEntity<List<AvailableDriverDto>> getAvailableDrivers(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5.0") double radiusKm,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) Integer limit) {

        GetAvailableDriversQuery query = new GetAvailableDriversQuery();
        query.setLatitude(latitude);
        query.setLongitude(longitude);
        query.setRadiusKm(radiusKm);
        if (vehicleType != null) {
            query.setVehicleType(com.ridesharing.domain.enums.VehicleType.valueOf(vehicleType));
        }
        query.setLimit(limit);

        List<AvailableDriverDto> drivers = getAvailableDriversHandler.handle(query);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/driver/{driverId}/earnings")
    public ResponseEntity<DriverEarningsDto> getDriverEarnings(
            @PathVariable String driverId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        GetDriverEarningsQuery query = new GetDriverEarningsQuery(driverId, startDate, endDate);
        DriverEarningsDto earnings = getDriverEarningsHandler.handle(query);
        return ResponseEntity.ok(earnings);
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<StartRideResponse> startRide(
            @PathVariable UUID rideId,
            @RequestBody StartRideCommand command) {
        command.setRideId(rideId);
        StartRideResponse response = startRideHandler.handle(command);
        return ResponseEntity.ok(response);
    }
}