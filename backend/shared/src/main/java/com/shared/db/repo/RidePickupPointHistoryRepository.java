package com.shared.db.repo;

import com.shared.db.entities.RidePickupPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RidePickupPointHistoryRepository extends JpaRepository<RidePickupPointHistory, Long> {

    void deleteAllByRideId(Long rideId);

    List<RidePickupPointHistory> findByRideId(Long rideId);

}
