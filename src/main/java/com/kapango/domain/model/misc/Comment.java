package com.kapango.domain.model.misc;

import com.kapango.application.dto.UserResponseDto;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record Comment(Integer id, ZonedDateTime created, ZonedDateTime updated, UserResponseDto commenter, String comment) {

}
