package com.kapango.domain.model.stats;

import java.time.Duration;
import lombok.Builder;

@Builder
public record IncidentStats(Integer incidentCount, Integer incidentCount30Days, Integer activeIncidents, Duration averageResolveTime,
                            Integer cancelledIncidents, Integer falseAlarmsCount, Integer falseAlarms30Days) {

}
