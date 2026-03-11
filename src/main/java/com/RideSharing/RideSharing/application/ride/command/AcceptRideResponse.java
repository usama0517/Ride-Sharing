package com.ridesharing.application.ride.command;

import com.ridesharing.domain.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptRideResponse {
    private boolean success;
    private String message;
    private RideStatus newStatus;
}