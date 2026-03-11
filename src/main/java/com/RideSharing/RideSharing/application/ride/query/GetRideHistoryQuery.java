package com.ridesharing.application.ride.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRideHistoryQuery {
    private String userId;
    private UserType userType;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer limit;

    public enum UserType {
        PASSENGER,
        DRIVER
    }
}