package com.kapango.infra.repository.misc;

import com.kapango.infra.entity.misc.MortyConfigEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MortyConfigDao extends JpaRepository<MortyConfigEntity, Integer>, JpaSpecificationExecutor<MortyConfigEntity> {

    Optional<MortyConfigEntity> findByConfigGroupNameContainingIgnoreCase(String key);
}