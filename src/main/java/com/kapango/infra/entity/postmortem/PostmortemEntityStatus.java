package com.kapango.infra.entity.postmortem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PostmortemEntityStatus {
    CREATED("Created"),
    TODO("To-do"),
    PAUSED("Paused"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");
    private final String name;

    PostmortemEntityStatus(String name) {
        assert name != null;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public static List<String> getAll() {
        return Arrays.stream(PostmortemEntityStatus.values()).map(postmortemState -> postmortemState.name).collect(Collectors.toList());
    }
}
