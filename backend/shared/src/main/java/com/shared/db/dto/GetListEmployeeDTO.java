package com.shared.db.dto;

import com.shared.db.entities.Bus;
import com.shared.db.entities.Employee;

public interface GetListEmployeeDTO {
    Employee getEmployee();
    Bus getBus();
}
