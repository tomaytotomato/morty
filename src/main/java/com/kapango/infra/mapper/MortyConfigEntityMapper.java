package com.kapango.infra.mapper;

import com.kapango.domain.model.misc.MortyConfig;
import com.kapango.domain.model.misc.Tag;
import com.kapango.infra.entity.misc.MortyConfigEntity;
import com.kapango.infra.entity.misc.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MortyConfigEntityMapper extends LazyLoadingAwareMapper {

    MortyConfigEntityMapper INSTANCE = Mappers.getMapper(MortyConfigEntityMapper.class);

    MortyConfigEntity toEntity(MortyConfig model);

    MortyConfig fromEntity(MortyConfigEntity entity);

}

