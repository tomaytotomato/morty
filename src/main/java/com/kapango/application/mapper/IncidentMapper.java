package com.kapango.application.mapper;

import com.kapango.application.dto.incident.CreateIncidentDto;
import com.kapango.application.dto.incident.IncidentDto;
import com.kapango.application.dto.incident.IncidentSeverityDto;
import com.kapango.application.dto.incident.IncidentTypeDto;
import com.kapango.domain.model.incident.Incident;
import com.kapango.domain.model.incident.IncidentSeverity;
import com.kapango.domain.model.incident.IncidentType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BaseMapper.class})
public interface IncidentMapper {

    IncidentMapper INSTANCE = Mappers.getMapper(IncidentMapper.class);

    Incident toModel(IncidentDto dto);

    IncidentDto fromModel(Incident model);

    Incident toModel(CreateIncidentDto dto);

    IncidentTypeDto fromModel(IncidentType model);

    IncidentType toModel(IncidentTypeDto dto);

    IncidentSeverityDto fromModel(IncidentSeverity model);

    IncidentSeverity toModel(IncidentSeverityDto dto);
}

