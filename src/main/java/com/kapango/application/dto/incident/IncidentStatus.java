package com.kapango.application.dto.incident;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum IncidentStatus {
    CREATED("Created"),
    PAUSED("Paused"),
    INVESTIGATING("Investigating"),
    FIXING("Fixing"),
    TESTING("Testing"),
    RESOLVED("Completed"),
    SELF_RESOLVED("Self Resolved"),
    FALSE_ALARM("False Alarm"),
    CANCELLED("Cancelled");
    private final String name;

    IncidentStatus(String name) {
        assert name != null;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getAll() {
        return Arrays.stream(IncidentStatus.values()).map(postmortemState -> postmortemState.name).collect(Collectors.toList());
    }
}
