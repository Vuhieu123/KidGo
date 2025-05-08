package com.shared.db.repo;

import com.shared.db.entities.EntityAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityAuthorizationRepository extends JpaRepository<EntityAuthorization, Long> {

    List<EntityAuthorization> findAllByIdStartingWithAndRoleIdIn(String prefix, List<String> roleIds);

}
