package com.kapango.domain.model.misc;

import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record Tag(Integer id, String name, ZonedDateTime created, ZonedDateTime updated) { }
