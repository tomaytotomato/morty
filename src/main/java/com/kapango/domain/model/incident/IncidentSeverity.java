package com.kapango.domain.model.incident;

import lombok.Builder;

@Builder
public record IncidentSeverity(Integer id, String name, String description) {

}
