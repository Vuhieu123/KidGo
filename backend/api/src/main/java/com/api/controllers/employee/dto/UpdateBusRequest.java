package com.api.controllers.employee.dto;

import com.api.services.bus.dto.UpdateBusEmployeeInput;
import com.shared.enumeration.BusStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBusRequest {

    private String numberPlate;

    private BusStatus status;

    public UpdateBusEmployeeInput toInput() {
        return UpdateBusEmployeeInput.builder()
                .numberPlate(numberPlate)
                .status(status)
                .build();
    }

}
