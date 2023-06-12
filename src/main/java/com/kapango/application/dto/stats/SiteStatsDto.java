package com.kapango.application.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteStatsDto {

    private Integer userCount;
    private Integer userCount24hr;
    private Integer apiRequests;
}
