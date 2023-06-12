package com.kapango.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto {

    Integer id;
    String name;
    String description;
    LocalDateTime created;
    LocalDateTime updated;

}
