package studio.startapps.pandemona.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.stats.internal.EndpointCounter;
import studio.startapps.pandemona.stats.internal.EndpointCounterRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EndpointCounterService {

    private final EndpointCounterRepository endpointCounterRepository;

    void logRequest(String endpoint) {
        logRequest(LocalDate.now(), endpoint);
    }

    void logRequest(LocalDate requestDate, String endpoint) {
        // find by requestDate / endpoint
        Optional<EndpointCounter> optionalEndpointCounter = this.endpointCounterRepository.findByEndpointAndDate(endpoint, requestDate);

        optionalEndpointCounter.ifPresentOrElse(
            endpointCounter -> {
                endpointCounter.setRequestCount(endpointCounter.getRequestCount() + 1);
                endpointCounterRepository.save(endpointCounter);
            },
            () -> {
                EndpointCounter endpointCounter = EndpointCounter.builder()
                        .requestDate(requestDate)
                        .requestEndpoint(endpoint)
                        .requestCount(1L)
                        .build();
                endpointCounterRepository.save(endpointCounter);
            }
        );
    }
}
