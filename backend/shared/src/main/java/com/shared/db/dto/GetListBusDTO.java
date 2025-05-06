package com.shared.db.dto;

import com.shared.db.entities.Bus;
import com.shared.db.entities.Employee;
import java.util.List;

public interface GetListBusDTO {
    Bus getBus();
    Employee getDriver();
    Employee getDriverMate();
}
