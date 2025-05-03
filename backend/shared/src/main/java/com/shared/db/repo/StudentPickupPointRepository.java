package com.shared.db.repo;

import com.shared.db.entities.StudentPickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPickupPointRepository extends JpaRepository<StudentPickupPoint, Long> {
}
