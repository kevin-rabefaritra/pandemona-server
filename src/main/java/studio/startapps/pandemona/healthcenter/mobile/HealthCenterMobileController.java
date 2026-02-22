package studio.startapps.pandemona.healthcenter.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.stats.internal.TrackEndpointUsage;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mobile/health-centers", version = "2.0")
@RequiredArgsConstructor
public class HealthCenterMobileController {

    private final HealthCenterMobileService healthCenterMobileService;

    @GetMapping
    @TrackEndpointUsage
    List<HealthCenterItem> findAll() {
        return this.healthCenterMobileService.findAll();
    }
}
