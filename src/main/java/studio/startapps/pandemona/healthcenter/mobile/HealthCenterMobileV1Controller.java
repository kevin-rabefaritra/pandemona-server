package studio.startapps.pandemona.healthcenter.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.stats.internal.TrackEndpointUsage;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mobile/v1/health-centers")
@RequiredArgsConstructor
@Deprecated
public class HealthCenterMobileV1Controller {

    private final HealthCenterMobileService healthCenterMobileService;

    @GetMapping
    @TrackEndpointUsage
    List<HealthCenterItem> findAll() {
        return this.healthCenterMobileService.findAll();
    }
}
