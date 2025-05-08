package com.api.services.parent_student.dto;

import com.api.services.common_dto.BusOutput;
import com.api.services.common_dto.PickupPointOutput;
import com.api.services.common_dto.RideOutput;
import com.api.services.common_dto.RidePickupPointOutput;
import com.api.services.common_dto.StudentOutput;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetStudentRideOutput {
    private StudentOutput student;
    private PickupPointOutput pickupPoint;
    private List<ExecutionOutput> executions;


    @Data
    public static class ExecutionOutput {
        private RideOutput ride;
        private BusOutput bus;
        private List<PickupPointOutput> pickupPoints;
        private List<RidePickupPointOutput> ridePickupPoints;
    }
}
