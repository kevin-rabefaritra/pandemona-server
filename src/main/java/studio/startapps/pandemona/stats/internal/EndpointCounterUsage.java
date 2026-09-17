package studio.startapps.pandemona.stats.internal;

import lombok.Builder;

@Builder
public record EndpointCounterUsage(
    String endpoint,
    String version,
    Long count
) {

    public EndpointCounterUsage(EndpointCounter e) {
        this(e.getRequestEndpoint(), e.getVersion(), e.getRequestCount());
    }
}
