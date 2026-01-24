package studio.startapps.pandemona.number.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.stats.internal.TrackEndpointUsage;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mobile/v1/numbers", version = "1.0")
@RequiredArgsConstructor
public class NumberMobileController {

    private final NumberMobileService numberMobileService;

    @GetMapping
    @TrackEndpointUsage
    List<EmergencyNumberItem> findAll() {
        return this.numberMobileService.findAll();
    }
}
