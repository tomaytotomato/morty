package com.kapango.application.mapper;

import com.kapango.application.dto.TagDto;
import com.kapango.domain.model.misc.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BaseMapper.class})
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    Tag toModel(TagDto dto);

    TagDto fromModel(Tag model);

}
