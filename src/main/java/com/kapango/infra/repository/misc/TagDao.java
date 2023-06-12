package com.kapango.infra.repository.misc;

import com.kapango.infra.entity.misc.TagEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends JpaRepository<TagEntity, Integer>, JpaSpecificationExecutor<TagEntity> {

    List<TagEntity> findByNameContainingIgnoreCase(String tagName);

    Optional<TagEntity> findByName(@Param("name") String name);
}