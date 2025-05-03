package com.shared.db.repo;

import com.shared.db.entities.RidePickupPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RidePickupPointHistoryRepository extends JpaRepository<RidePickupPointHistory, Long> {
}
