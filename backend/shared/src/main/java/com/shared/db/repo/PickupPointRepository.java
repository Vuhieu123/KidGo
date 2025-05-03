package com.shared.db.repo;

import com.shared.db.entities.PickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupPointRepository extends JpaRepository<PickupPoint, Long> {
}
