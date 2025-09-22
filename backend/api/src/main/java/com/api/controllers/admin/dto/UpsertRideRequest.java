package com.api.controllers.admin.dto;

import com.api.services.ride.dto.UpsertRideInput;
import com.shared.utils.DateConvertUtil;
import java.util.List;
import lombok.Data;

@Data
public class UpsertRideRequest {

    private Long id;

    private Long busId;

    private String startAt;

    private String endAt;

    private String startFrom;

    private Boolean isToSchool;

    private List<Long> pickupPointIds;

    public UpsertRideInput toInput() {
        return UpsertRideInput.builder()
                .id(id)
                .busId(busId)
                .startAt(DateConvertUtil.convertStringTimeStampToInstant(startAt))
                .endAt(DateConvertUtil.convertStringTimeStampToInstant(endAt))
                .startFrom(startFrom)
                .pickupPointIds(pickupPointIds)
                .isToSchool(isToSchool)
                .build();
    }

}