package com.kapango.domain.model.postmortem;

import com.kapango.domain.model.misc.Tag;
import com.kapango.infra.entity.user.UserEntity;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Builder;

@Builder
public record Postmortem(Integer id, String reference, Long version, ZonedDateTime created, ZonedDateTime updated, ZonedDateTime completed,
                         String name, String description, String status, String source, String severity, String rootCause, Set<Tag> tags,
                         UserEntity author, boolean createdByHuman) {

}
