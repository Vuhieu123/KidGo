package com.shared.db.dto;

import com.shared.db.entities.Bus;
import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.Ride;
import com.shared.db.entities.RidePickupPoint;
import com.shared.db.entities.Student;
import java.util.List;

public interface GetStudentRideDTO {
    Student getStudent();
    PickupPoint getPickupPoint();
    List<ExecutionDTO> getExecutions();

    interface ExecutionDTO {
        Ride getRide();
        Bus getBus();
        List<PickupPoint> getPickupPoints();
        List<RidePickupPoint> getRidePickupPoints();
    }

}
