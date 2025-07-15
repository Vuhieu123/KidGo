package com.api.controllers.client;

import com.api.services.history.HistoryService;
import com.api.services.history.dto.ClientHistoryRideFilterParam;
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
@RequestMapping("/api/v1/client/history")
@RequiredArgsConstructor
@Slf4j
public class ClientHistoryController {
    private final HistoryService historyService;

    @GetMapping("/ride/pagination")
    public ResponseEntity<CommonResponse<Object>> getAdminHistoryRides(
            ClientHistoryRideFilterParam filterParam,
            @AuthenticationPrincipal Account account
    ) {
        Pageable pageable = PageableUtils.generate(
                filterParam.getPage(),
                filterParam.getSize(),
                filterParam.getSort(),
                "-id"
        );

        return ResponseUtil.toSuccessCommonResponse(
                historyService.getClientHistoryRides(filterParam, pageable, account)
        );
    }
}
