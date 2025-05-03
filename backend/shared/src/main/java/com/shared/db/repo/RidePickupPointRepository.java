package com.shared.db.repo;

import com.shared.db.entities.RidePickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RidePickupPointRepository extends JpaRepository<RidePickupPoint, Long> {
}
