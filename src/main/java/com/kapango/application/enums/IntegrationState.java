package com.kapango.application.enums;

public enum IntegrationState {
    STABLE("Stable", "This integration should work fine with Morty"),
    EXPERIMENTAL("Experimental","This integration is in Beta and may have errors or not work with Morty"),
    SOON("Coming Soon","This integration will be coming soon");
    private final String name;
    private final String description;

    IntegrationState(String name, String description) {
        assert name != null;
        assert description != null;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return description;
    }
}
