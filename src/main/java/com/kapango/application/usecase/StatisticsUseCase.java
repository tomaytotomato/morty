package com.kapango.application.usecase;

import com.kapango.domain.model.stats.PostmortemStats;
import com.kapango.domain.model.stats.SiteStats;
import com.kapango.domain.model.stats.IncidentStats;

/**
 * Gives stats on various aspects of Morty e.g. Incidents, Postmortems
 */
public interface StatisticsUseCase {

    IncidentStats getIncidentStats();

    PostmortemStats getPostmortemStats();

    SiteStats getGenericStats();

}
