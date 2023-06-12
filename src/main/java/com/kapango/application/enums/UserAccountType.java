package com.kapango.application.enums;

public enum UserAccountType {
    ADMIN("Admin", "Administrator has full privileges."),
    USER("User", "Has basic permissions to create incidents and postmortems."),
    API("API", "For use by apps and automated tasks that are not performed by a human."),
    GUEST("Guest", "Can only view public contents on Morty"),
    DEACTIVATED("Deactivated", "Cannot login or access Morty");

    private final String name;
    private final String description;
    UserAccountType(String name, String description) {
        assert name != null;
        assert description != null;
        this.name = name;
        this.description = description;
    }
}
