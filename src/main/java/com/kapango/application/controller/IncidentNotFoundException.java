package com.kapango.application.controller;

public class IncidentNotFoundException extends RuntimeException {

    public IncidentNotFoundException(Integer id) {
        super("Could not find Incident with ID : " + id);
    }
}
