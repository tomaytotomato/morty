package com.kapango.infra.mapper;

import com.kapango.domain.model.misc.Tag;
import com.kapango.infra.entity.misc.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagEntityMapper extends LazyLoadingAwareMapper {

    TagEntityMapper INSTANCE = Mappers.getMapper(TagEntityMapper.class);

    TagEntity toEntity(Tag model);

    Tag fromEntity(TagEntity entity);

}

