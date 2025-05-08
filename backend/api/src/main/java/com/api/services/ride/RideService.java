package com.api.services.ride;

import com.api.services.ride.dto.UpdateRideEmployeeInput;
import com.api.services.ride.dto.UpsertRideInput;
import com.api.services.ride.dto.UpdateRideInput;
import com.shared.db.entities.Account;

public interface RideService {
    void upsertRide(UpsertRideInput upsertRideInput);

    void updateRide(UpdateRideInput updateRideInput);

    void updateRideEmployee(UpdateRideEmployeeInput updateRideEmployeeInput, Account account);
}
