package com.shared.db.repo;

import com.shared.db.entities.RideHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideHistoryRepository extends JpaRepository<RideHistory, Long> {
}
