package studio.startapps.pandemona.ondutydrugstores.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresRepository;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OnDutyDrugstoresMobileService {

    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;

    List<OnDutyDrugstoresItem> findAll(Pageable pageable) {
        LocalDate today = LocalDate.now();
        return this.onDutyDrugstoresRepository.findAllByEndDateAfter(today, pageable).map(OnDutyDrugstoresItem::new).toList();
    }
}
