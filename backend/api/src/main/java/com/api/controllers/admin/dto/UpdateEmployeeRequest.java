package com.api.controllers.admin.dto;

import com.api.services.employee.dto.UpdateEmployeeInput;
import com.shared.enumeration.BusStatus;
import com.shared.enumeration.EmployeeRole;
import com.shared.utils.DateConvertUtil;
import java.time.Instant;
import lombok.Data;

@Data
public class UpdateEmployeeRequest {

    private Long id;

    private String name;

    private String phoneNumber;

    private String dob;

    private String avatar;

    private Long busId;

    private EmployeeRole role;

    private String busNumberPlate;

    public UpdateEmployeeInput toInput() {
        return UpdateEmployeeInput.builder()
                .id(id)
                .name(name)
                .phoneNumber(phoneNumber)
                .dob(DateConvertUtil.convertStringToInstant(dob))
                .avatar(avatar)
                .busId(busId)
                .role(role)
                .busNumberPlate((busNumberPlate == null || busNumberPlate.isEmpty()) ? null : busNumberPlate)
                .build();
    }

}
