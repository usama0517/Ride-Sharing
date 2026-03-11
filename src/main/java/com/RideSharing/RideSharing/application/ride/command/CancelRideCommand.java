package com.ridesharing.application.ride.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelRideCommand {
    private UUID rideId;
    private String cancelledBy; // "PASSENGER" or "DRIVER"
    private String reason;
}