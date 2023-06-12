package com.kapango.infra.mapper;

import com.kapango.domain.model.incident.Incident;
import com.kapango.domain.model.incident.IncidentSeverity;
import com.kapango.domain.model.incident.IncidentType;
import com.kapango.infra.entity.incident.IncidentEntity;
import com.kapango.infra.entity.incident.IncidentSeverityEntity;
import com.kapango.infra.entity.incident.IncidentStatus;
import com.kapango.infra.entity.incident.IncidentTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IncidentEntityMapper extends LazyLoadingAwareMapper {

    IncidentEntityMapper INSTANCE = Mappers.getMapper(IncidentEntityMapper.class);

    IncidentEntity toEntity(Incident model);

    Incident fromEntity(IncidentEntity entity);

    void update(@MappingTarget IncidentEntity entity, IncidentEntity updateEntity);

    IncidentStatus fromStatus(com.kapango.domain.model.incident.IncidentStatus status);

    IncidentType fromEntity(IncidentTypeEntity entity);

    IncidentTypeEntity toEntity(IncidentType model);

    IncidentSeverity fromEntity(IncidentSeverityEntity entity);

    IncidentSeverityEntity toEntity(IncidentSeverity incidentSeverity);
}

