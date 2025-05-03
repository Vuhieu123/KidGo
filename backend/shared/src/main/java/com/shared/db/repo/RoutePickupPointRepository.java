package com.shared.db.repo;

import com.shared.db.entities.RoutePickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutePickupPointRepository extends JpaRepository<RoutePickupPoint, Long> {
}
