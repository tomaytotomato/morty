package com.kapango.application.dto.incident;

import com.kapango.application.dto.CommentDto;
import com.kapango.application.dto.TagDto;
import com.kapango.application.dto.UserDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDto {

    private Integer id;
    private String reference;
    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime resolved;
    private String description;
    private IncidentStatus status;
    private String source;
    private IncidentTypeDto type;
    private IncidentSeverityDto severity;
    private IncidentPriority priority;
    private String rootCause;
    private Set<TagDto> tags;
    private List<IncidentEventDto> events;
    private List<CommentDto> comments;
    private HashMap<String, String> metaData;
    private UserDto reporter;
    private List<UserDto> testers;
    private List<UserDto> developers;
    private boolean createdByHuman;
}
