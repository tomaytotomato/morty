package com.kapango.infra.mapper;

import com.kapango.domain.filter.PostmortemFilter;
import com.kapango.domain.model.postmortem.Postmortem;
import com.kapango.infra.entity.postmortem.PostmortemEntity;
import com.kapango.infra.filter.PostmortemJpaFilter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostmortemEntityMapper extends LazyLoadingAwareMapper {

    PostmortemEntityMapper INSTANCE = Mappers.getMapper(PostmortemEntityMapper.class);

    PostmortemEntity toEntity(Postmortem postmortemModel);

    Postmortem fromEntity(PostmortemEntity postmortemEntity);

    PostmortemJpaFilter toJpaFilter(PostmortemFilter filter);


}

