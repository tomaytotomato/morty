package com.kapango.infra.mapper;

import com.kapango.domain.filter.UserFilter;
import com.kapango.domain.model.user.User;
import com.kapango.domain.model.user.UserRole;
import com.kapango.infra.entity.user.UserEntity;
import com.kapango.infra.entity.user.UserRoleEntity;
import com.kapango.infra.filter.UserJpaFilter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserEntityMapper extends LazyLoadingAwareMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserEntity toEntity(User userModel);

    User fromEntity(UserEntity userEntity);

    UserJpaFilter toFilter(UserFilter filter);

    UserRole fromEntity(UserRoleEntity userRole);

    UserRoleEntity toEntity(UserRole userRole);
}

