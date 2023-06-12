package com.kapango.infra.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * This config class should only be used by classes in the Infra package {@link com.kapango.infra}
 */
@Configuration
@ConfigurationProperties("morty.infra")
@Data
@Primary
public class MortyInfraConfig {

    private String adminUsername;
    private String adminPassword;
    private String incidentSeveritiesSeedFile;
    private String incidentTypesSeedFile;
}
