package com.kapango.domain.model.incident;

import com.kapango.application.dto.incident.IncidentEventDto;
import com.kapango.domain.model.misc.Comment;
import com.kapango.domain.model.misc.Tag;
import com.kapango.domain.model.user.User;
import com.kapango.infra.entity.incident.IncidentStatus;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
public record Incident(Integer id, String reference, String name, ZonedDateTime created, ZonedDateTime updated, ZonedDateTime resolved,
                       String description, IncidentStatus status, String source, IncidentType type, IncidentSeverity severity,
                       String rootCause, Set<Tag> tags, List<IncidentEventDto> events, List<Comment> comments,
                       HashMap<String, String> metaData, User reporter, List<User> testers, List<User> developers, boolean createdByHuman) {

}
