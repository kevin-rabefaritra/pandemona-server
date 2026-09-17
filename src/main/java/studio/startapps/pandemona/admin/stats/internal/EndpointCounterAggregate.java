package studio.startapps.pandemona.admin.stats.internal;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EndpointCounterAggregate(
    LocalDate period,
    List<EndpointCounterUsage> usage
) {
}
