package com.shared.db.repo;

import com.shared.db.entities.StudentAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentAssignRepository extends JpaRepository<StudentAssign, Long> {
    Optional<StudentAssign> findByStudentId(Long studentId);
}
