package com.api.services.history;

import com.api.services.history.dto.AdminHistoryRideFilterParam;
import com.api.services.history.dto.AdminHistoryRideOutput;
import com.api.services.history.dto.ClientHistoryRideFilterParam;
import com.api.services.history.dto.ClientHistoryRideOutput;
import com.api.services.history.dto.EmployeeHistoryRideFilterParam;
import com.api.services.history.dto.EmployeeHistoryRideOutput;
import com.shared.db.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryService {
    Page<AdminHistoryRideOutput> getAdminHistoryRides(AdminHistoryRideFilterParam filterParam,
                                                      Pageable pageable);

    Page<EmployeeHistoryRideOutput> getEmployeeHistoryRides(
            EmployeeHistoryRideFilterParam filterParam,
            Pageable pageable, Account account);

    Page<ClientHistoryRideOutput> getClientHistoryRides(
            ClientHistoryRideFilterParam filterParam,
            Pageable pageable, Account account);
}
