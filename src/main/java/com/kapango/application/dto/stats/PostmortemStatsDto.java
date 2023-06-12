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
public class PostmortemStatsDto {

    private Integer count;
    private Integer completedCount;
    private Integer cancelledCount;
    private Integer todoCount;
    private Duration averageCompletedTime;

}
