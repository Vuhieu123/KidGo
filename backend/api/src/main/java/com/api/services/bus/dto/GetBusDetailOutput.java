package com.api.services.bus.dto;

import com.api.services.common_dto.BusOutput;
import com.api.services.common_dto.EmployeeOutput;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBusDetailOutput {
    BusOutput bus;
    EmployeeOutput driver;
    EmployeeOutput driverMate;
}
