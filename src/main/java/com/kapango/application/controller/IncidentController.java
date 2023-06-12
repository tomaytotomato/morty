package com.kapango.application.controller;


import com.kapango.application.dto.incident.CreateIncidentDto;
import com.kapango.application.dto.incident.IncidentDto;
import com.kapango.application.mapper.IncidentMapper;
import com.kapango.application.usecase.IncidentUseCase;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {

    private final IncidentUseCase incidentService;
    private final IncidentMapper mapper;

    @Autowired
    public IncidentController(IncidentUseCase incidentService, IncidentMapper mapper) {
        this.incidentService = incidentService;
        this.mapper = mapper;
    }

    @GetMapping("/api/incidents")
    public List<IncidentDto> getIncidents() {
        return incidentService.findAll()
            .stream().map(mapper::fromModel)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/incident/{id}")
    public IncidentDto getIncidentById(@PathVariable("id") Integer id) {
        return incidentService.findById(id)
            .map(mapper::fromModel).orElseThrow(() -> new IncidentNotFoundException(id));
    }

    @PostMapping("/api/incident")
    public IncidentDto createIncident(@RequestBody CreateIncidentDto newIncident) {
        return mapper.fromModel(incidentService.upsert(mapper.toModel(newIncident)));
    }
}
