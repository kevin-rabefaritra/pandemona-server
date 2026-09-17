package studio.startapps.pandemona.mobile.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.admin.stats.internal.TrackEndpointUsage;

@RestController
@RequestMapping(value = "api/mobile/v1/report")
@RequiredArgsConstructor
@Deprecated
public class ClientReportMobileControllerV1 {

    private final ClientReportMobileService reportMobileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @TrackEndpointUsage
    void save(@RequestBody SaveReportRequest request) {
        this.reportMobileService.submit(request);
    }
}
