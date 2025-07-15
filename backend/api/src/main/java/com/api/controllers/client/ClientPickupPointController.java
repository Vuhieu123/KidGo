package com.api.controllers.client;

import com.api.controllers.admin.dto.PickupPointFilterParam;
import com.api.services.account.AccountService;
import com.api.services.pickup_point.PickupPointService;
import com.shared.db.entities.Account;
import com.shared.response.CommonResponse;
import com.shared.utils.PageableUtils;
import com.shared.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client/pickup-point")
@RequiredArgsConstructor
@Slf4j
public class ClientPickupPointController {
    private final PickupPointService pickupPointService;
    private final AccountService accountService;

}