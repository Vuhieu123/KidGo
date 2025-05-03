package com.shared.db.repo;

import com.shared.db.entities.RequestRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRegistrationRepository extends JpaRepository<RequestRegistration, Long> {
}
