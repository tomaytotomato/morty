package com.kapango.infra.repository.incident;

import com.kapango.application.usecase.IncidentUseCase;
import com.kapango.domain.model.incident.Incident;
import com.kapango.domain.model.incident.IncidentSeverity;
import com.kapango.domain.model.incident.IncidentStatus;
import com.kapango.domain.model.incident.IncidentType;
import com.kapango.infra.mapper.IncidentEntityMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
public class IncidentRepository implements IncidentUseCase {

    private final IncidentDao incidentDao;
    private final IncidentTypeDao typeDao;

    private final IncidentSeverityDao severityDao;
    private final IncidentEntityMapper mapper;

    public IncidentRepository(IncidentDao incidentDao, IncidentTypeDao typeDao, IncidentSeverityDao severityDao,
                              IncidentEntityMapper mapper) {
        this.incidentDao = incidentDao;
        this.typeDao = typeDao;
        this.severityDao = severityDao;
        this.mapper = mapper;
    }

    public List<Incident> findAll() {
        return incidentDao.findAll()
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public Optional<Incident> findById(Integer id) {
        return incidentDao.findById(id)
            .map(mapper::fromEntity);
    }

    public List<Incident> findAllByName(String name) {
        return incidentDao.findAllByNameContainingIgnoreCase(name)
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public List<Incident> findAllByReference(String reference) {
        return incidentDao.findAllByReferenceContainingIgnoreCase(reference)
            .stream().map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    public Incident upsert(Incident incident) {
        if (Objects.isNull(incident)) {
            throw new IllegalArgumentException("Incident cannot be null when upserting");
        }

        if (!Objects.isNull(incident.id())) {
            var result = incidentDao.findById(incident.id());

            if (result.isPresent()) {
                var existingEntity = result.get();
                mapper.update(existingEntity, mapper.toEntity(incident));
                return mapper.fromEntity(incidentDao.save(existingEntity));
            }
        }
        var entityToSave = mapper.toEntity(incident);
        return mapper.fromEntity(incidentDao.save(entityToSave));
    }

    public Integer count() {
        return Math.toIntExact(incidentDao.count());
    }

    public Integer countByStatus(IncidentStatus status) {
        return incidentDao.countAllByStatus(mapper.fromStatus(status));
    }

    public void delete(Incident incident) {
        if (!Objects.isNull(incident) && !Objects.isNull(incident.id())) {
            incidentDao.deleteById(incident.id());
        }
    }

    @Override
    public List<IncidentType> findAllIncidentTypes(String incidentType) {
        if (StringUtils.isNotBlank(incidentType)) {
            return typeDao.findAllByNameContainingIgnoreCase(incidentType)
                .stream().map(mapper::fromEntity)
                .collect(Collectors.toList());
        } else {
            return typeDao.findAll()
                .stream().map(mapper::fromEntity)
                .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<IncidentType> findByIncidentTypeId(Integer id) {
        return typeDao.findById(id)
            .map(mapper::fromEntity);
    }

    @Override
    public IncidentType upsert(IncidentType incidentType) {

        if (Objects.isNull(incidentType)) {
            throw new IllegalArgumentException("Incident Type cannot be null when upserting");
        }

        if (!Objects.isNull(incidentType.id())) {
            var result = typeDao.findById(incidentType.id());

            if (result.isPresent()) {
                var existingEntity = result.get();
                existingEntity.setName(incidentType.name());
                existingEntity.setDescription(incidentType.description());
                return mapper.fromEntity(typeDao.save(existingEntity));
            }
        }
        var entityToSave = mapper.toEntity(incidentType);
        return mapper.fromEntity(typeDao.save(entityToSave));
    }

    @Override
    public void delete(IncidentType incidentType) {
        if (!Objects.isNull(incidentType) && !Objects.isNull(incidentType.id())) {
            typeDao.deleteById(incidentType.id());
        }
    }

    @Override
    public List<IncidentSeverity> findAllIncidentSeverities(String incidentSeverity) {
        if (StringUtils.isNotBlank(incidentSeverity)) {
            return severityDao.findAllByNameContainingIgnoreCase(incidentSeverity)
                .stream().map(mapper::fromEntity)
                .collect(Collectors.toList());
        } else {
            return severityDao.findAll()
                .stream().map(mapper::fromEntity)
                .collect(Collectors.toList());
        }
    }

    @Override
    public IncidentSeverity upsert(IncidentSeverity incidentSeverity) {
        if (Objects.isNull(incidentSeverity)) {
            throw new IllegalArgumentException("Incident Severity cannot be null when upserting");
        }

        if (!Objects.isNull(incidentSeverity.id())) {
            var result = severityDao.findById(incidentSeverity.id());

            if (result.isPresent()) {
                var existingEntity = result.get();
                existingEntity.setName(incidentSeverity.name());
                existingEntity.setDescription(incidentSeverity.description());
                return mapper.fromEntity(severityDao.save(existingEntity));
            }
        }
        var entityToSave = mapper.toEntity(incidentSeverity);
        return mapper.fromEntity(severityDao.save(entityToSave));
    }

    @Override
    public Optional<IncidentSeverity> findByIncidentSeverityId(Integer incidentSeverityId) {
        return severityDao.findById(incidentSeverityId).map(mapper::fromEntity);
    }

    public void delete(IncidentSeverity incidentSeverity) {
        if (!Objects.isNull(incidentSeverity) && !Objects.isNull(incidentSeverity.id())) {
            severityDao.deleteById(incidentSeverity.id());
        }
    }
}
