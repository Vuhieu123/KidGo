package com.shared.db.repo;

import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.RoutePickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutePickupPointRepository extends JpaRepository<RoutePickupPoint, Long> {
    void deleteAllByPickupPoint(PickupPoint pickupPoint);
}
