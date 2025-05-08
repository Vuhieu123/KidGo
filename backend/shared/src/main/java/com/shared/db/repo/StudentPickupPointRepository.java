package com.shared.db.repo;

import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.Student;
import com.shared.db.entities.StudentPickupPoint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPickupPointRepository extends JpaRepository<StudentPickupPoint, Long> {

    void deleteAllByPickupPoint(PickupPoint pickupPoint);

    boolean existsByPickupPointId(Long pickupPointId);

    void deleteByStudent(Student student);

    void deleteByStudentId(Long studentId);

    Optional<StudentPickupPoint> findByStudentIdAndPickupPointId(Long studentId, Long pickupPointId);
    List<StudentPickupPoint> findByPickupPointIdIn(List<Long> pickupPointIds);

}
