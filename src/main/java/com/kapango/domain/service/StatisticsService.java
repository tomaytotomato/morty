package com.kapango.domain.service;

import com.kapango.application.usecase.StatisticsUseCase;
import com.kapango.domain.model.stats.PostmortemStats;
import com.kapango.domain.model.stats.SiteStats;
import com.kapango.domain.model.stats.IncidentStats;
import com.kapango.infra.repository.incident.IncidentRepository;
import com.kapango.infra.repository.postmortem.PostmortemRepository;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService implements StatisticsUseCase {

    private final IncidentRepository incidentRepository;
    private final PostmortemRepository postmortemRepository;

    public StatisticsService(IncidentRepository incidentRepository, PostmortemRepository postmortemRepository) {
        this.incidentRepository = incidentRepository;
        this.postmortemRepository = postmortemRepository;
    }


    @Override
    public IncidentStats getIncidentStats() {
        return IncidentStats.builder()
            .incidentCount(23)
            .incidentCount30Days(10)
            .cancelledIncidents(2)
            .falseAlarmsCount(1)
            .falseAlarms30Days(3)
            .activeIncidents(1)
            .averageResolveTime(Duration.ofHours(11))
            .build();
    }

    @Override
    public PostmortemStats getPostmortemStats() {
        return null;
    }

    @Override
    public SiteStats getGenericStats() {
        return null;
    }
}
