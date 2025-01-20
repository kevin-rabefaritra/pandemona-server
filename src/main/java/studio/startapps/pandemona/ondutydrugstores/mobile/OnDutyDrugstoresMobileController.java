package studio.startapps.pandemona.ondutydrugstores.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/mobile/v1/on-duty-drugstores")
@RequiredArgsConstructor
public class OnDutyDrugstoresMobileController {

    private final OnDutyDrugstoresMobileService onDutyDrugstoresMobileService;

    @GetMapping
    List<OnDutyDrugstoresItem> findAll(Pageable pageable) {
        return this.onDutyDrugstoresMobileService.findAll(pageable);
    }
}
