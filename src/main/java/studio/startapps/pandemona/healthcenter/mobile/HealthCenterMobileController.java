package studio.startapps.pandemona.healthcenter.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mobile/v1/health-centers")
@RequiredArgsConstructor
public class HealthCenterMobileController {

    private final HealthCenterMobileService healthCenterMobileService;

    @GetMapping
    List<HealthCenterItem> findAll() {
        return this.healthCenterMobileService.findAll();
    }
}
