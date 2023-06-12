package com.kapango.infra.repository.incident;

import com.kapango.infra.entity.incident.IncidentTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentTypeDao extends JpaRepository<IncidentTypeEntity, Integer>, JpaSpecificationExecutor<IncidentTypeEntity> {

    List<IncidentTypeEntity> findAllByNameContainingIgnoreCase(String name);
}