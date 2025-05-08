package com.shared.db.dto;

import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.Ride;
import com.shared.db.entities.RidePickupPoint;

public interface GetListRidePickupPointDTO {
    Ride getRide();
    PickupPoint getPickupPoint();
    RidePickupPoint getRidePickupPoint();
}
