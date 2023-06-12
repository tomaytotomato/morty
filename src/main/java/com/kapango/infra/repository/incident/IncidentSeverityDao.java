package com.kapango.infra.repository.incident;

import com.kapango.infra.entity.incident.IncidentSeverityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentSeverityDao extends JpaRepository<IncidentSeverityEntity, Integer>, JpaSpecificationExecutor<IncidentSeverityEntity> {

    List<IncidentSeverityEntity> findAllByNameContainingIgnoreCase(String name);

}