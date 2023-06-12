package com.kapango.infra.repository.incident;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.kapango.domain.model.incident.Incident;
import com.kapango.domain.model.incident.IncidentSeverity;
import com.kapango.domain.model.incident.IncidentStatus;
import com.kapango.domain.model.incident.IncidentType;
import com.kapango.infra.entity.incident.IncidentEntity;
import com.kapango.infra.entity.incident.IncidentSeverityEntity;
import com.kapango.infra.entity.incident.IncidentTypeEntity;
import com.kapango.infra.mapper.IncidentEntityMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class IncidentRepositoryTest {

    @Mock
    private IncidentDao incidentDao;

    @Mock
    private IncidentTypeDao typeDao;

    @Mock
    private IncidentSeverityDao severityDao;

    private IncidentEntityMapper mapper;

    @InjectMocks
    private IncidentRepository incidentRepository;

    @BeforeEach
    public void setup() {
        mapper = IncidentEntityMapper.INSTANCE;
        ReflectionTestUtils.setField(incidentRepository, "mapper", mapper);
    }

    @Test
    public void findAll_ShouldReturnListOfIncidents() {
        // Given
        var entities = List.of(IncidentEntity.builder().id(123).name("Incident A").build(),
            IncidentEntity.builder().id(2169).name("Incident X").build());

        when(incidentDao.findAll()).thenReturn(entities);

        // When
        var result = incidentRepository.findAll();

        // Then
        assertThat(result).hasSize(2).extracting("name").containsExactly("Incident A", "Incident X");
        verify(incidentDao).findAll();
    }

    @Test
    public void findById_WhenIncidentIdExists_ThenReturnIncident() {
        // Given
        var id = 1;
        var name = "Incident ABC";
        var entity = IncidentEntity.builder().id(id).name(name).build();

        // When
        when(incidentDao.findById(id)).thenReturn(Optional.of(entity));
        var result = incidentRepository.findById(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo(name);
        verify(incidentDao).findById(id);
    }

    @Test
    public void findById_WhenIncidentIdDoesNotExist_ThenReturnEmptyOptional() {
        // Given
        var id = 1612;

        // When
        when(incidentDao.findById(id)).thenReturn(Optional.empty());
        var result = incidentRepository.findById(id);

        // Then
        assertThat(result).isEmpty();
        verify(incidentDao).findById(id);
    }

    @Test
    public void findAllByName_WhenIncidentNameIsNotBlank_ShouldReturnFilteredListOfIncidents() {
        // Given
        var name = "FUBAR";
        var entities = List.of(IncidentEntity.builder().id(123).name("FUBAR 215").build(),
            IncidentEntity.builder().id(2169).name("FUBAR 125").build());

        when(incidentDao.findAllByNameContainingIgnoreCase(name)).thenReturn(entities);

        // When
        var result = incidentRepository.findAllByName(name);

        // Then
        assertThat(result).hasSize(2).extracting("name").containsExactly("FUBAR 215", "FUBAR 125");
        verify(incidentDao).findAllByNameContainingIgnoreCase(name);
    }

    @Test
    public void findAllByReference_WhenReferenceIsNotBlank_ThenReturnFilteredListOfIncidents() {
        // Given
        var reference = "REF-";
        var entities = List.of(IncidentEntity.builder().id(123).name("FUBAR 215").reference("REF-123").build(),
            IncidentEntity.builder().id(2169).name("FUBAR 125").reference("REF-794").build());

        when(incidentDao.findAllByReferenceContainingIgnoreCase(reference)).thenReturn(entities);

        // When
        var result = incidentRepository.findAllByReference(reference);

        // Then
        assertThat(result).hasSize(2).extracting("reference").containsExactly("REF-123", "REF-794");
        verify(incidentDao).findAllByReferenceContainingIgnoreCase(reference);
    }

    @Test
    public void upsert_WhenIncidentExists_ThenUpdateAndReturnUpdatedIncident() {
        // Given
        var incident = Incident.builder().id(126).name("Existing Incident").reference("REF-123").build();

        var existingEntity = IncidentEntity.builder().id(126).name("Existing Incident").build();

        var updatedEntity = IncidentEntity.builder().id(126).name("Existing Incident").reference("REF-123").build();

        // When
        when(incidentDao.findById(incident.id())).thenReturn(Optional.of(existingEntity));
        when(incidentDao.save(updatedEntity)).thenReturn(updatedEntity);
        var result = incidentRepository.upsert(incident);

        // Then
        assertThat(result).isEqualTo(incident);
        verify(incidentDao).findById(incident.id());
        verify(incidentDao).save(updatedEntity);
    }

    @Test
    public void upsert_WhenIncidentDoesNotExist_ThenSaveAndReturnIncident() {
        // Given
        var incident = Incident.builder().name("Incident to save").description("SAVEME123").build();

        var savedEntity = new IncidentEntity();
        savedEntity.setId(1);
        savedEntity.setName("Incident to save");

        // When
        when(incidentDao.save(mapper.toEntity(incident))).thenReturn(savedEntity);
        var result = incidentRepository.upsert(incident);

        // Then
        assertThat(result).isEqualTo(mapper.fromEntity(savedEntity));
        verify(incidentDao, never()).findById(any());
        verify(incidentDao).save(mapper.toEntity(incident));
        verifyNoMoreInteractions(incidentDao);
    }

    @Test
    public void upsert_WhenIncidentIsNull_ThenThrowIllegalArgumentException() {
        // Given
        Incident incident = null;

        // When & Then
        assertThatThrownBy(() -> incidentRepository.upsert(incident)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incident cannot be null when upserting");
    }

    @Test
    public void count_ShouldReturnNumberOfIncidents() {
        // Given
        long count = 10L;
        when(incidentDao.count()).thenReturn(count);

        // When
        var result = incidentRepository.count();

        // Then
        assertThat(result).isEqualTo(Math.toIntExact(count));
        verify(incidentDao).count();
    }

    @Test
    public void countByStatus_WhenStatus_ThenReturnCountForStatus() {
        // Given
        var count = 3;
        var status = IncidentStatus.TESTING;

        // When
        when(incidentDao.countAllByStatus(mapper.fromStatus(status))).thenReturn(count);
        var result = incidentRepository.countByStatus(status);

        // Then
        assertThat(result).isEqualTo(count);
        verify(incidentDao).countAllByStatus(mapper.fromStatus(status));
    }

    @Test
    public void delete_WhenValidIncident_ThenDelete() {
        // Given
        var incident = Incident.builder().id(1).build();

        // When
        incidentRepository.delete(incident);

        // Then
        verify(incidentDao).deleteById(incident.id());
    }

    @Test
    public void delete_WhenInValidIncident_ThenDoNothing() {
        // Given
        var incident = Incident.builder().id(null).build();

        // When
        incidentRepository.delete(incident);

        // Then
        verifyNoInteractions(incidentDao);
    }

    @Test
    public void findAllIncidentTypes_WhenIncidentTypeIsNotBlank_ThenReturnAListOfMatchingIncidentTypes() {
        // Given
        var incidentType = "TPS Report";
        var entities = List.of(IncidentTypeEntity.builder().id(56).name("TPS Report 123").build(),
            IncidentTypeEntity.builder().id(689).name("TPS Report Peter 20692").build());

        // When
        when(typeDao.findAllByNameContainingIgnoreCase(incidentType)).thenReturn(entities);
        var result = incidentRepository.findAllIncidentTypes(incidentType);

        // Then
        assertThat(result).hasSize(2);
        verify(typeDao).findAllByNameContainingIgnoreCase(incidentType);
    }

    @Test
    public void findAllIncidentTypes_WhenIncidentTypeIsBlank_ThenReturnAllIncidentTypes() {
        // Given
        var entities = List.of(new IncidentTypeEntity(), new IncidentTypeEntity());

        // When
        when(typeDao.findAll()).thenReturn(entities);
        var result = incidentRepository.findAllIncidentTypes("");

        // Then
        assertThat(result).hasSize(2);
        verify(typeDao).findAll();
    }

    @Test
    public void findByIncidentTypeId_WhenIncidentTypeIdExists_ThenReturnIncidentType() {
        // Given
        var id = 1;
        var entity = IncidentTypeEntity.builder().id(1).name("I Exist").build();

        // When
        when(typeDao.findById(id)).thenReturn(Optional.of(entity));

        var result = incidentRepository.findByIncidentTypeId(id);

        // Then
        assertThat(result).isPresent();
        verify(typeDao).findById(id);
    }

    @Test
    public void findByIncidentTypeId_WhenIncidentTypeIdDoesNotExist_ThenReturnEmptyOptional() {
        // Given
        var id = 1;

        // When
        when(typeDao.findById(id)).thenReturn(Optional.empty());
        var result = incidentRepository.findByIncidentTypeId(id);

        // Then
        assertThat(result).isEmpty();
        verify(typeDao).findById(id);
    }

    @Test
    public void upsertIncidentType_WhenIncidentTypeExists_ThenUpdateAndReturnIncidentType() {
        // Given
        var incidentType = IncidentType.builder().id(1).name("New Name").build();

        var existingEntity = IncidentTypeEntity.builder().id(1).name("Old name").build();

        var updatedEntity = IncidentTypeEntity.builder()
            .id(1)
            .name("New name")
            .build();

        // When
        when(typeDao.findById(incidentType.id())).thenReturn(Optional.of(existingEntity));
        when(typeDao.save(existingEntity)).thenReturn(updatedEntity);
        var result = incidentRepository.upsert(incidentType);

        // Then
        assertThat(result)
            .usingRecursiveComparison()
            .ignoringFields("id", "name", "description")
            .isEqualTo(incidentType);
        verify(typeDao).findById(incidentType.id());
        verify(typeDao).save(existingEntity);
    }

    @Test
    public void upsertIncidentType_WhenIncidentTypeIsNull_ThenThrowIllegalArgumentException() {
        // Given
        IncidentType incidentType = null;

        // When & Then
        assertThatThrownBy(() -> incidentRepository.upsert(incidentType)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incident Type cannot be null when upserting");
    }

    @Test
    public void upsertIncidentType_WhenIncidentTypeDoesNotExist_ThenSaveAndReturnIncidentType() {
        // Given
        var incidentType = IncidentType.builder().id(null).name("Save me").build();

        var savedEntity = IncidentTypeEntity.builder().id(1).name("Save me").build();

        // When
        when(typeDao.save(mapper.toEntity(incidentType))).thenReturn(savedEntity);
        var result = incidentRepository.upsert(incidentType);

        // Then
        assertThat(result).isEqualTo(mapper.fromEntity(savedEntity));
        verify(typeDao, times(0)).findById(any());
        verify(typeDao).save(mapper.toEntity(incidentType));
        verifyNoMoreInteractions(typeDao);
    }

    @Test
    public void deleteIncidentType_WhenValidIncidentType_ThenDeleteIncidentType() {
        // Given
        var incidentType = new IncidentType(123, "Delete me", "DELETE", false);

        // When
        incidentRepository.delete(incidentType);

        // Then
        verify(typeDao).deleteById(incidentType.id());
    }

    @Test
    public void deleteIncidentType_WhenInValidIncidentType_ThenDoNothing() {
        // Given
        var incidentType = new IncidentType(null, "I'm null", "DELETE", false);

        // When
        incidentRepository.delete(incidentType);

        // Then
        verifyNoInteractions(typeDao);
    }

    @Test
    public void findAllIncidentSeverities_WithValidIncidentSeverity_ShouldReturnListOfIncidentSeverities() {
        // Given
        var incidentSeverityName = "Sev";
        var severityEntities = List.of(IncidentSeverityEntity.builder().id(1).name("Sev1").build(),
            IncidentSeverityEntity.builder().id(2).name("Sev2").build());

        // When
        when(severityDao.findAllByNameContainingIgnoreCase(incidentSeverityName)).thenReturn(severityEntities);

        var result = incidentRepository.findAllIncidentSeverities(incidentSeverityName);

        // Then
        assertThat(result).hasSize(2).extracting("name").containsExactly("Sev1", "Sev2");
        verify(severityDao).findAllByNameContainingIgnoreCase(incidentSeverityName);
    }

    @Test
    public void findAllIncidentSeverities_WhenNoSeveritySet_ThenReturnAllSeverities() {
        // Given
        var severityEntities = List.of(IncidentSeverityEntity.builder().id(5).name("Foo").build(),
            IncidentSeverityEntity.builder().id(6).name("Bobby").build());

        // When
        when(severityDao.findAll()).thenReturn(severityEntities);
        var result = incidentRepository.findAllIncidentSeverities("");

        // Then
        assertThat(result).hasSize(2).extracting("name").containsExactly("Foo", "Bobby");
        verify(severityDao).findAll();
    }

    @Test
    public void findByIncidentSeverityId_WhenSeverityIdExists_ThenReturnSeverity() {
        // Given
        var id = 1652126;
        var existingEntity = IncidentSeverityEntity.builder().name("I exist").description("I live in the database").build();

        // When
        when(severityDao.findById(id)).thenReturn(Optional.of(existingEntity));
        var result = incidentRepository.findByIncidentSeverityId(id);

        // Then
        assertThat(result).isPresent().get().isEqualTo(mapper.fromEntity(existingEntity));
        verify(severityDao).findById(id);
    }

    @Test
    public void findByIncidentSeverityId_WhenSeverityIdDoesNotExist_ThenReturnEmpty() {
        // Given
        var id = 117;

        // When
        when(severityDao.findById(id)).thenReturn(Optional.empty());
        var result = incidentRepository.findByIncidentSeverityId(id);

        // Then
        assertThat(result).isEmpty();
        verify(severityDao).findById(id);
    }

    @Test
    public void upsertIncidentSeverity_WhenSeverityExists_ThenUpdateAndReturnSeverity() {
        // Given
        var name = "Update me";
        var id = 24601;
        var modelToUpsert = IncidentSeverity.builder().id(id).name(name).build();

        var existingEntity = IncidentSeverityEntity.builder().id(id).name("I am not updated").build();
        var updatedEntity = IncidentSeverityEntity.builder().id(id).name(name).build();


        var expected = IncidentSeverity.builder().id(id).name(name).build();

        // When
        when(severityDao.findById(id)).thenReturn(Optional.of(existingEntity));
        when(severityDao.save(existingEntity)).thenReturn(updatedEntity);
        var result = incidentRepository.upsert(modelToUpsert);

        // Then
        assertThat(result).isEqualTo(expected);
        verify(severityDao).findById(id);
        verify(severityDao).save(existingEntity);
        verifyNoMoreInteractions(severityDao);
    }

    @Test
    public void upsertIncidentSeverity_WhenNullSeverity_ThenThrowIllegalArgumentException() {
        // Given
        IncidentSeverity incidentSeverity = null;

        // When & Then
        assertThatThrownBy(() -> incidentRepository.upsert(incidentSeverity)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incident Severity cannot be null when upserting");
    }

    @Test
    public void upsertIncidentSeverity_WhenSeverityDoesNotExist_ThenSaveAndReturnSeverity() {
        // Given
        var name = "Save me";
        var modelToUpsert = IncidentSeverity.builder().id(null).name(name).build();

        var savedEntity = IncidentSeverityEntity.builder().id(123).name(name).build();

        // When
        when(severityDao.save(mapper.toEntity(modelToUpsert))).thenReturn(savedEntity);

        var result = incidentRepository.upsert(modelToUpsert);

        // Then
        assertThat(result).isEqualTo(mapper.fromEntity(savedEntity));
        verify(severityDao, never()).findById(any());
        verify(severityDao).save(mapper.toEntity(modelToUpsert));
        verifyNoMoreInteractions(severityDao);
    }

    @Test
    public void deleteIncidentSeverity_WhenIdIsValid_ThenDeleteSeverity() {
        // Given
        var id = 123;
        var modelToDelete = IncidentSeverity.builder().id(id).build();

        // When
        incidentRepository.delete(modelToDelete);

        // Then
        verify(severityDao).deleteById(id);
    }

    @Test
    public void deleteIncidentSeverity_WhenIdIsNull_ThenDoNothing() {
        // Given
        var modelToDelete = IncidentSeverity.builder().id(null).build();

        // When
        incidentRepository.delete(modelToDelete);

        // Then
        verifyNoInteractions(severityDao);
    }
}
