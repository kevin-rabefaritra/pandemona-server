package studio.startapps.pandemona.drugstore.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.drugstore.internal.Drugstore;
import studio.startapps.pandemona.drugstore.internal.DrugstoreRepository;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstores;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service  @RequiredArgsConstructor
@Deprecated
public class LegacyDrugstoreMobileService {

    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;
    private final DrugstoreRepository drugstoreRepository;

    Iterable<Drugstore> findDrugstoresByUpdatedAtGreaterThanEqual(LocalDateTime dateTime) {
        return this.drugstoreRepository.findByUpdatedAtGreaterThanEqual(dateTime);
    }

    Iterable<OnDutyDrugstores> findOnDutyDrugstoresByUpdatedAtGreaterThanEqual(LocalDateTime dateTime) {
        return this.onDutyDrugstoresRepository.findByUpdatedAtGreaterThanEqual(dateTime);
    }

    Iterable<Drugstore> findAllDeletedDrugstores() {
        return this.drugstoreRepository.findAllDeleted();
    }

    Iterable<OnDutyDrugstores> findAllDeletedOnDutyDrugstores() {
        return this.onDutyDrugstoresRepository.findAllDeleted();
    }
}
