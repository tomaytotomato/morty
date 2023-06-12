package com.kapango.application.dto.incident;

public enum IncidentPriority {
    P1("Level 1", "Priority 1", "A major outage preventing users from performing tasks or impacting the whole organisation severely."),
    P2("Level 2", "Priority 2", "An outage preventing some users from performing tasks or impacting a part of the organisation severely."),
    P3("Level 3", "Priority 3", "An outage limited to some users or impacting specific teams or business functions in the organisation."),
    P4("Level 4", "Priority 4", "A limited outage only impacting specific users or teams within the organisations");

    private String name;
    private String altName;
    private String description;

    IncidentPriority(String name, String altName, String description) {
        assert description != null;
        assert name != null;
        assert altName != null;
        this.name = name;
        this.altName = altName;
        this.description = description;
    }
}
