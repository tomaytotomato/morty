package com.kapango.application.dto;

import com.kapango.application.enums.PostmortemStatus;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostmortemDto {

    private Integer id;
    private String reference;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime completed;
    private String name;
    private String description;
    private PostmortemStatus status;
    private Integer incidentId;
    private String rootCause;
    private Set<TagDto> tags;
    private UserResponseDto author;
    private boolean createdByHuman;
}
