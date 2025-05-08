package com.api.services.ride.dto;

import com.shared.enumeration.RideStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRideEmployeeInput {
    private Long rideId;
    private RideStatus status;
}
