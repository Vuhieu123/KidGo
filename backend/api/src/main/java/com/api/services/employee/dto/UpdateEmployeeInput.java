package com.api.services.employee.dto;

import com.shared.enumeration.EmployeeRole;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEmployeeInput {
    private Long id;
    private String name;
    private String phoneNumber;
    private Instant dob;
    private String avatar;
    private Long busId;
    private EmployeeRole role;
    private String busNumberPlate;
}
