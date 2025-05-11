package com.api.controllers.admin.dto;

import com.api.services.ride_pickup_point.dto.UpdateRidePickupPointInput;
import com.shared.enumeration.RidePickupPointStatus;
import lombok.Data;

@Data
public class UpdateRidePickupPointRequest {
    private Long id;
    private RidePickupPointStatus status;

    public UpdateRidePickupPointInput toInput() {
        return UpdateRidePickupPointInput.builder()
                .id(id)
                .status(status)
                .build();
    }
}
