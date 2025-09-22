package com.api.controllers.employee;

import com.api.controllers.employee.dto.UpdateStudentPickupPointRequest;
import com.api.services.student_pickup_point.StudentPickupPointService;
import com.shared.db.entities.Account;
import com.shared.response.CommonResponse;
import com.shared.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee/student-pickup-point")
@RequiredArgsConstructor
@Slf4j
public class EmployeeStudentPickupPointController {

    private final StudentPickupPointService studentPickupPointService;

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<CommonResponse<Object>> updateStudentPickupPoints(
            @RequestBody UpdateStudentPickupPointRequest request,
            @AuthenticationPrincipal Account account
    ) {
        studentPickupPointService.updateStudentPickupPointEmployee(request.toInput(), account);
        return ResponseUtil.toSuccessCommonResponse("Update student pickup point successfully");
    }

}