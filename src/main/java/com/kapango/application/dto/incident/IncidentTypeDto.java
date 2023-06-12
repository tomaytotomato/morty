package com.kapango.application.dto.incident;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentTypeDto {
    private Integer id;
    @NotEmpty(message = "Incident type cannot be empty")
    private String name;
    @NotEmpty(message = "The description for an incident type must be set.")
    private String description;
}
