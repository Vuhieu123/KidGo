package com.shared.db.repo;

import com.shared.db.entities.EntityAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityAuthorizationRepository extends JpaRepository<EntityAuthorization, Long> {
    // Custom query methods can be defined here if needed
}
