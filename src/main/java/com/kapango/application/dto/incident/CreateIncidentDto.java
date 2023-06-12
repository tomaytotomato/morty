package com.kapango.application.dto.incident;

import com.kapango.application.dto.TagDto;
import com.kapango.application.dto.UserDto;
import java.util.HashMap;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateIncidentDto {
    private String reference;
    private String name;
    private String description;
    private IncidentStatus status = IncidentStatus.CREATED;
    private IncidentTypeDto type;
    private IncidentSeverityDto severity;
    private IncidentPriority priority;
    private String source;
    private Set<TagDto> tags;
    private HashMap<String, String> metaData;
    private UserDto reporter;
}
