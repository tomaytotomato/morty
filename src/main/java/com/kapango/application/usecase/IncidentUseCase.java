package com.kapango.application.usecase;

import com.kapango.domain.model.incident.Incident;
import com.kapango.domain.model.incident.IncidentSeverity;
import com.kapango.domain.model.incident.IncidentType;
import java.util.List;
import java.util.Optional;

public interface IncidentUseCase extends CrudUseCase<Incident> {

    List<IncidentType> findAllIncidentTypes(String incidentType);

    Optional<IncidentType> findByIncidentTypeId(Integer id);
    IncidentType upsert(IncidentType incidentType);

    void delete(IncidentType incidentType);

    Optional<IncidentSeverity> findByIncidentSeverityId(Integer id);

    List<IncidentSeverity> findAllIncidentSeverities(String incidentSeverity);

    IncidentSeverity upsert(IncidentSeverity incidentSeverity);

    void delete(IncidentSeverity incidentSeverity);
}
