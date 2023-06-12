package com.kapango.domain.model.user;

import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record User(Integer id, ZonedDateTime created, ZonedDateTime updated, String username, String firstName, String lastName,
                   String hashedPassword, UserAccountType accountType, UserRole role, String team, String position, byte[] profilePicture,
                   boolean important) {

}
