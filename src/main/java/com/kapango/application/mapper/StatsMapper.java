package com.kapango.application.mapper;

import com.kapango.application.dto.stats.IncidentStatsDto;
import com.kapango.application.dto.stats.PostmortemStatsDto;
import com.kapango.application.dto.stats.SiteStatsDto;
import com.kapango.domain.model.stats.PostmortemStats;
import com.kapango.domain.model.stats.SiteStats;
import com.kapango.domain.model.stats.IncidentStats;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BaseMapper.class})
public interface StatsMapper {

    StatsMapper INSTANCE = Mappers.getMapper(StatsMapper.class);

    IncidentStatsDto fromModel(IncidentStats model);

    PostmortemStatsDto fromModel(PostmortemStats model);
    SiteStatsDto fromModel(SiteStats model);

}

