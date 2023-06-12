package com.kapango.domain.service;

import com.kapango.application.usecase.IncidentUseCase;
import com.kapango.domain.model.incident.Incident;
import com.kapango.domain.model.incident.IncidentSeverity;
import com.kapango.domain.model.incident.IncidentType;
import com.kapango.infra.repository.incident.IncidentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncidentService implements IncidentUseCase {

    private final IncidentRepository repository;

    @Autowired
    public IncidentService(IncidentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Incident> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Incident> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Incident upsert(Incident newIncident) throws IncidentExistsException {
        return repository.upsert(newIncident);
    }

    @Override
    public void delete(Incident model) {
        repository.delete(model);
    }

    @Override
    public List<IncidentType> findAllIncidentTypes(String incidentType) {
        return repository.findAllIncidentTypes(incidentType);
    }

    @Override
    public Optional<IncidentType> findByIncidentTypeId(Integer id) {
        return repository.findByIncidentTypeId(id);
    }

    @Override
    public IncidentType upsert(IncidentType incidentType) {
        return repository.upsert(incidentType);
    }

    @Override
    public void delete(IncidentType incidentType) {
        repository.delete(incidentType);
    }

    @Override
    public Optional<IncidentSeverity> findByIncidentSeverityId(Integer id) {
        return repository.findByIncidentSeverityId(id);
    }

    @Override
    public List<IncidentSeverity> findAllIncidentSeverities(String incidentSeverity) {
        return repository.findAllIncidentSeverities(incidentSeverity);
    }

    @Override
    public IncidentSeverity upsert(IncidentSeverity incidentSeverity) {
        return repository.upsert(incidentSeverity);
    }

    @Override
    public void delete(IncidentSeverity incidentSeverity) {
        repository.delete(incidentSeverity);
    }
}
