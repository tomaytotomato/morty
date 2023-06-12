package com.kapango.infra.repository.postmortem;

import com.kapango.infra.entity.postmortem.PostmortemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostmortemDao extends JpaRepository<PostmortemEntity, Integer>, JpaSpecificationExecutor<PostmortemEntity> {

}