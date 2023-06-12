package com.kapango.application.mapper;

import com.kapango.application.dto.UserDto;
import com.kapango.application.dto.UserRoleDto;
import com.kapango.domain.model.user.User;
import com.kapango.domain.model.user.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BaseMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDto dto);

    UserDto fromModel(User model);

    UserRoleDto fromModel(UserRole model);

    UserRole toModel(UserRoleDto dto);
}

