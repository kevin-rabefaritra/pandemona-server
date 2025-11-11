package studio.startapps.pandemona.drugstore.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.stats.internal.TrackEndpointUsage;

import java.util.List;

@RestController
@RequestMapping("/api/mobile/v1/drugstores")
@RequiredArgsConstructor
public class DrugstoreMobileController {

    private final DrugstoreMobileService drugstoreMobileService;

    @GetMapping
    @TrackEndpointUsage
    List<DrugstoreItem> findAll() {
        return this.drugstoreMobileService.findAll();
    }
}
