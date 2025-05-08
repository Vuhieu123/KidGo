package com.api.services.bus.dto;

import com.shared.db.entities.Bus;
import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.Ride;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetListManipulateBusOutPut {
    private Bus bus;
    private Ride ride;
    private List<PickupPoint> pickupPoints;
}
