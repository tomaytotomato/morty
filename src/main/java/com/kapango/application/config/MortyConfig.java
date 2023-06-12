package com.kapango.application.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * This config class should only be used by classes in the Infra package {@link com.kapango.application}
 */
@Configuration
@ConfigurationProperties("morty.app")
@Data
@Primary
public class MortyConfig {

}
