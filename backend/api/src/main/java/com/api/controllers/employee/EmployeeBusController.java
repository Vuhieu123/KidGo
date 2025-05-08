package com.api.controllers.employee;

import com.api.controllers.employee.dto.UpdateBusRequest;
import com.api.services.bus.BusService;
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
@RequestMapping("/api/v1/employee/bus")
@RequiredArgsConstructor
@Slf4j
public class EmployeeBusController {
    private final BusService busService;

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<CommonResponse<Object>> updateBus(
            @RequestBody UpdateBusRequest request,
            @AuthenticationPrincipal Account account
    ) {
        busService.updateBusEmployee(request.toInput(), account);
        return ResponseUtil.toSuccessCommonResponse("Update bus successfully");
    }
}
