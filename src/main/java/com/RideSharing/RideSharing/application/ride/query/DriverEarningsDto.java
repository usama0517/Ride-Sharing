package com.ridesharing.application.ride.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverEarningsDto {
    private BigDecimal totalEarnings;
    private BigDecimal cashEarnings;
    private BigDecimal cardEarnings;
    private BigDecimal digitalWalletEarnings;
    private int totalRides;
    private double averageRating;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
}