package com.kapango.infra.repository.incident;

import com.kapango.infra.entity.incident.IncidentEntity;
import com.kapango.infra.entity.incident.IncidentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentDao extends JpaRepository<IncidentEntity, Integer>, JpaSpecificationExecutor<IncidentEntity> {

    List<IncidentEntity> findAllByReferenceContainingIgnoreCase(String reference);

    List<IncidentEntity> findAllByNameContainingIgnoreCase(String name);

    Integer countAllByStatus(IncidentStatus status);
}