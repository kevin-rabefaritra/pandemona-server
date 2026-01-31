package studio.startapps.pandemona.ondutydrugstores.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.stats.internal.TrackEndpointUsage;

import java.util.List;

@RestController
@RequestMapping(value = "api/mobile/v1/on-duty-drugstores")
@RequiredArgsConstructor
@Deprecated
public class OnDutyDrugstoresMobileV1Controller {

    private final OnDutyDrugstoresMobileService onDutyDrugstoresMobileService;

    @GetMapping
    @TrackEndpointUsage
    List<OnDutyDrugstoresItem> findAll(@PageableDefault(sort = "startDate") Pageable pageable) {
        return this.onDutyDrugstoresMobileService.findAll(pageable);
    }
}
