package com.kapango.application.dto.incident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentSeverityDto {

    private Integer id;
    private String name;
    private String description;
    private boolean anticipated;

}
