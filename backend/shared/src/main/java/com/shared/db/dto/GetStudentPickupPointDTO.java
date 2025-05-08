package com.shared.db.dto;

import com.shared.db.entities.Parent;
import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.Ride;
import com.shared.db.entities.Student;
import java.util.List;

public interface GetStudentPickupPointDTO {
    PickupPoint getPickupPoint();
    Student getStudent();
}
