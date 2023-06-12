package com.kapango.application.mapper;

import com.kapango.application.dto.PostmortemDto;
import com.kapango.application.dto.PostmortemFilterDto;
import com.kapango.domain.filter.PostmortemFilter;
import com.kapango.domain.model.postmortem.Postmortem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BaseMapper.class})
public interface PostmortemMapper {

    PostmortemMapper INSTANCE = Mappers.getMapper(PostmortemMapper.class);

    Postmortem toModel(PostmortemDto dto);

    PostmortemDto fromModel(Postmortem model);


    PostmortemFilter toFilterModel(PostmortemFilterDto filter);

}

