package com.ridesharing.application.ride.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDriverEarningsQuery {
    private String driverId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}