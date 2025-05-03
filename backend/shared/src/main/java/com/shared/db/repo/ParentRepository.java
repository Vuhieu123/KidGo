package com.shared.db.repo;

import com.shared.db.dto.GetParentAndChildDTO;
import com.shared.db.entities.Parent;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("""
    SELECT p
    FROM Parent p
    LEFT JOIN Account a ON a.id = p.account.id
    LEFT JOIN Student s ON s.parent.id = p.id
    WHERE (:id IS NULL OR p.id = :id)
      AND (:role IS NULL OR a.role = :role)
      AND (
        :searchType IS NULL OR :name IS NULL OR :name = '' OR
        (
          (:searchType = 'PARENT_NAME' AND (p.name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))))
          OR (:searchType = 'STUDENT_NAME' AND (s.name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))))
          OR (:searchType = 'PARENT_PHONE_NUMBER' AND (p.phoneNumber IS NULL OR p.phoneNumber LIKE CONCAT('%', :name, '%')))
        )
      )
      AND (:phoneNumber IS NULL OR p.phoneNumber LIKE CONCAT('%', :phoneNumber, '%'))
      AND (:studentId IS NULL OR s.id = :studentId)
""")
    Page<Parent> searchPageParent(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("role") String role,
            @Param("searchType") String searchType,
            @Param("phoneNumber") String phoneNumber,
            @Param("studentId") Long studentId,
            Pageable pageable
    );

    @Query(value = """
            select p as parent
            from Parent p
            where p.account.id = :accountId
        """)
    Optional<Parent> findByAccountId(Long accountId);

}
