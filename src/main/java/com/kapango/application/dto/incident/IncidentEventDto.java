package com.kapango.application.dto.incident;


import com.kapango.application.dto.UserResponseDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidentEventDto {

    private Integer id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private IncidentEventTypeDto type;
    private String comment;
    private UserResponseDto user;
}
