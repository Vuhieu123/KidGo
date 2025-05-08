package com.api.services.common_dto;

import com.shared.db.entities.Bus;
import com.shared.db.entities.Ride;
import com.shared.enumeration.RideStatus;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideOutput {
    private Long id;
    private Long busId;
    private Boolean isToSchool;
    private Instant startAt;
    private Instant endAt;
    private String startFrom;
    private RideStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public static RideOutput fromEntity(Ride entity) {
        return RideOutput.builder()
                .id(entity.getId())
                .busId(entity.getBus().getId())
                .isToSchool(entity.getIsToSchool())
                .startAt(entity.getStartAt())
                .endAt(entity.getEndAt())
                .startFrom(entity.getStartFrom())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
