package com.kapango.infra.repository.user;

import com.kapango.infra.entity.user.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends JpaRepository<UserRoleEntity, Integer>, JpaSpecificationExecutor<UserRoleEntity> {

}