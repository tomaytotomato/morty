package com.kapango.domain.model.user;

import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record UserRole(Integer id, String name, String description, ZonedDateTime created, ZonedDateTime updated) { }
