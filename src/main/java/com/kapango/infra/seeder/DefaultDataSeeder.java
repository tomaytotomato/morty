package com.kapango.infra.seeder;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapango.infra.config.MortyInfraConfig;
import com.kapango.infra.entity.incident.IncidentSeverityEntity;
import com.kapango.infra.entity.incident.IncidentTypeEntity;
import com.kapango.infra.repository.incident.IncidentSeverityDao;
import com.kapango.infra.repository.incident.IncidentTypeDao;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "Default Data Seeder")
public class DefaultDataSeeder {

    private final MortyInfraConfig config;
    private final ObjectMapper mapper;
    private final IncidentSeverityDao incidentSeverityDao;
    private final IncidentTypeDao incidentTypeDao;

    public DefaultDataSeeder(MortyInfraConfig config, ObjectMapper mapper,
                             IncidentSeverityDao incidentSeverityDao, IncidentTypeDao incidentTypeDao) {
        this.config = config;
        this.mapper = mapper;
        this.incidentSeverityDao = incidentSeverityDao;
        this.incidentTypeDao = incidentTypeDao;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) throws URISyntaxException, IOException {
        seedIncidentSeverityTable();
        seedIncidentTypeTable();
    }



    private void seedIncidentSeverityTable() throws URISyntaxException, IOException {
        if (incidentSeverityDao.count() == 0) {
            log.info("Incident Severity table needs to be seeded");
            final URL incidentSeverities = getClass().getClassLoader().getResource(config.getIncidentSeveritiesSeedFile());
            var severities = mapper.readValue(new File(incidentSeverities.toURI()), new TypeReference<List<IncidentSeverityEntity>>() {});

            log.info("There are {} incident severities to loaded to the database", severities.size());

            incidentSeverityDao.saveAll(severities);
        }
    }

    private void seedIncidentTypeTable() throws URISyntaxException, IOException {
        if (incidentTypeDao.count() == 0) {
            log.info("Incident Type table needs to be seeded");
            final URL incidentType = getClass().getClassLoader().getResource(config.getIncidentTypesSeedFile());
            var incidentTypes = mapper.readValue(new File(incidentType.toURI()), new TypeReference<List<IncidentTypeEntity>>() {});

            log.info("There are {} incident types to loaded to the database", incidentTypes.size());

            incidentTypeDao.saveAll(incidentTypes);
        }
    }

}
