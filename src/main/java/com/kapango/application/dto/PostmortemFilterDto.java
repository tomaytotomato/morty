package com.kapango.application.dto;

import com.kapango.application.enums.PostmortemStatus;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostmortemFilterDto implements FilterDto {

    private Integer id;
    private String reference;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private ZonedDateTime completed;
    private String name;
    private String description;
    private PostmortemStatus status;
    private String source;
    private String severity;
    private String rootCause;
    private String tags;

}
