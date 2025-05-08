package com.shared.db.repo;

import com.shared.db.entities.StudentPickupPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPickupPointHistoryRepository extends JpaRepository<StudentPickupPointHistory, Long> {
    List<StudentPickupPointHistory> findByRideId(Long rideId);
    List<StudentPickupPointHistory> findByRideIdAndStudentId(Long rideId, Long studentId);
}
