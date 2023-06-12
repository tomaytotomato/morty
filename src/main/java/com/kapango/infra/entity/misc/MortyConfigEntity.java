package com.kapango.infra.entity.misc;

import com.kapango.infra.converter.HashMapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Used by Morty service for recording state or config values
 */
@Entity
@Table(name = "morty")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MortyConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @EqualsAndHashCode.Include
    private Integer id;
    @Version
    private Long version;
    @EqualsAndHashCode.Include
    private String configGroupName;
    @EqualsAndHashCode.Include
    @Convert(converter = HashMapConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> values;
    @CreationTimestamp
    private ZonedDateTime created;
    @UpdateTimestamp
    private ZonedDateTime updated;

}
