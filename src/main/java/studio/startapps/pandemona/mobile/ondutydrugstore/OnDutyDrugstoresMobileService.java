package studio.startapps.pandemona.mobile.ondutydrugstore;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.admin.ondutydrugstore.internal.OnDutyDrugstoresRepository;

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
