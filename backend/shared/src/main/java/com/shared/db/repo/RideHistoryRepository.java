package com.shared.db.repo;

import com.shared.db.entities.RideHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideHistoryRepository extends JpaRepository<RideHistory, Long> {
    List<RideHistory> findByRideId(Long rideId);
}
