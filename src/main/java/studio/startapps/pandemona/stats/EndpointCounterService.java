package studio.startapps.pandemona.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.stats.internal.EndpointCounter;
import studio.startapps.pandemona.stats.internal.EndpointCounterAggregate;
import studio.startapps.pandemona.stats.internal.EndpointCounterRepository;
import studio.startapps.pandemona.stats.internal.EndpointCounterUsage;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EndpointCounterService {

    private final EndpointCounterRepository endpointCounterRepository;

    EndpointCounterAggregate findSummary(LocalDate date) {
        List<EndpointCounter> endpointCounterList = endpointCounterRepository.findAllByDate(date);
        return EndpointCounterAggregate.builder()
                .period(date)
                .usage(endpointCounterList.stream().map(EndpointCounterUsage::new).toList())
                .build();
    }

    List<EndpointCounterAggregate> findSummary(LocalDate start, LocalDate end) {
        List<LocalDate> dateRange = DateUtils.range(start, end);
        return dateRange.parallelStream().map(this::findSummary).toList();
    }

    void logRequest(String endpoint, String version) {
        logRequest(LocalDate.now(), endpoint, version);
    }

    void logRequest(LocalDate requestDate, String endpoint, String version) {
        // find by requestDate / endpoint
        Optional<EndpointCounter> optionalEndpointCounter = endpointCounterRepository.findByEndpointDateAndVersion(endpoint, requestDate, version);

        optionalEndpointCounter.ifPresentOrElse(
            endpointCounter -> {
                endpointCounter.setRequestCount(endpointCounter.getRequestCount() + 1);
                endpointCounterRepository.save(endpointCounter);
            },
            () -> {
                EndpointCounter endpointCounter = EndpointCounter.builder()
                        .requestDate(requestDate)
                        .requestEndpoint(endpoint)
                        .version(version)
                        .requestCount(1L)
                        .build();
                endpointCounterRepository.save(endpointCounter);
            }
        );
    }
}
