package com.kapango.domain.model.incident;

import lombok.Builder;

@Builder
public record IncidentType(Integer id, String name, String description, boolean anticipated) {



}
