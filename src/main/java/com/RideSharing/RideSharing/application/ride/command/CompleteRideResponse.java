package com.ridesharing.application.ride.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRideResponse {
    private boolean success;
    private String message;
    private double finalFare;
    private String receiptUrl;
}