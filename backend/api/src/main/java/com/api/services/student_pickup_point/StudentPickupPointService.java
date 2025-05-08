package com.api.services.student_pickup_point;

import com.api.services.student_pickup_point.dto.UpdateStudentPickupPointEmployeeInput;
import com.shared.db.entities.Account;

public interface StudentPickupPointService {
    void updateStudentPickupPointEmployee(UpdateStudentPickupPointEmployeeInput input,
                                          Account account);
}
