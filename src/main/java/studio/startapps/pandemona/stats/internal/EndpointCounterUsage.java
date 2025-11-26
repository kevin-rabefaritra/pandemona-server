package studio.startapps.pandemona.stats.internal;

import lombok.Builder;

@Builder
public record EndpointCounterUsage(
    String endpoint,
    Long count
) {

    public EndpointCounterUsage(EndpointCounter e) {
        this(e.getRequestEndpoint(), e.getRequestCount());
    }
}
