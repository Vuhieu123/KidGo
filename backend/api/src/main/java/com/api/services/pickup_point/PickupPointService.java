package com.api.services.pickup_point;

import com.api.controllers.admin.dto.PickupPointFilterParam;
import com.api.services.common_dto.RideOutput;
import com.api.services.pickup_point.dto.AddPickupPointInput;
import com.api.services.pickup_point.dto.GetListPickupPointOutput;
import com.api.services.pickup_point.dto.ManipulatePickupPointOutput;
import com.api.services.pickup_point.dto.UpdatePickupPointInput;
import com.shared.db.entities.Account;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PickupPointService {
    Page<GetListPickupPointOutput> getListPickupPoint(
            PickupPointFilterParam filterParam,
            Pageable pageable
    );

    void addPickupPoint(AddPickupPointInput input);

    void updatePickupPoint(UpdatePickupPointInput input);

    void deletePickupPoint(Long id);

    ManipulatePickupPointOutput getListManipulatePickupPoint(Account account, Instant date,
                                                             Long rideId);

    List<RideOutput> getListRideId(Account account, Instant date);
}
