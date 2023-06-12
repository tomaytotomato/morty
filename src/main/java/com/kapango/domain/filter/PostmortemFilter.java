package com.kapango.domain.filter;

import com.kapango.domain.model.postmortem.PostmortemStatus;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostmortemFilter implements Filter {

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
