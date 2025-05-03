package com.shared.db.repo;

import com.shared.db.entities.StudentAssign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAssignRepository extends JpaRepository<StudentAssign, Long> {
}
