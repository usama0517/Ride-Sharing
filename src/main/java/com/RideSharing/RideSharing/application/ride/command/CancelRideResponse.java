package com.ridesharing.application.ride.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelRideResponse {
    private boolean success;
    private String message;
    private BigDecimal cancellationFee;
    private String refundStatus;
}