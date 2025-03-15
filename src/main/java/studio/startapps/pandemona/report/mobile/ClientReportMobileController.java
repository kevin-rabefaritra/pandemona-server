package studio.startapps.pandemona.report.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mobile/v1/report")
@RequiredArgsConstructor
public class ClientReportMobileController {

    private final ClientReportMobileService reportMobileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody SaveReportRequest request) {
        this.reportMobileService.submit(request);
    }
}
