package studio.startapps.pandemona.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.stats.internal.EndpointCounterAggregate;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/endpoints")
@RequiredArgsConstructor
public class EndpointController {

    private final EndpointCounterService endpointCounterService;

    @GetMapping("/summary")
    List<EndpointCounterAggregate> findSummary(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return this.endpointCounterService.findSummary(start, end);
    }
}
