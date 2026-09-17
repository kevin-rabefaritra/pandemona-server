package studio.startapps.pandemona.mobile.drugstore;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.admin.stats.internal.TrackEndpointUsage;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mobile/drugstores", version = "2.0")
@RequiredArgsConstructor
public class DrugstoreMobileController {

    private final DrugstoreMobileService drugstoreMobileService;

    @GetMapping
    @TrackEndpointUsage
    List<DrugstoreItem> findAll() {
        return this.drugstoreMobileService.findAll();
    }
}
