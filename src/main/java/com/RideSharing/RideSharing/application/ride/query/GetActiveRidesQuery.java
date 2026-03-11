package com.ridesharing.application.ride.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetActiveRidesQuery {
    private String driverId;
    private String passengerId;
    private String status;
}