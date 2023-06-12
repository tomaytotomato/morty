package com.kapango.application.dto.stats;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidentStatsDto {

    private Integer incidentCount;
    private Integer incidentCount30Days;
    private Integer activeIncidents;
    private Duration averageResolveTime;
    private Integer cancelledIncidents;
    private Integer falseAlarmsCount;
    private Integer falseAlarms30Days;

}
