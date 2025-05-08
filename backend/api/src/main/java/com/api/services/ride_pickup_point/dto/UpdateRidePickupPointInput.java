package com.api.services.ride_pickup_point.dto;

import com.shared.enumeration.RidePickupPointStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRidePickupPointInput {
    private Long id;
    private RidePickupPointStatus status;
}
