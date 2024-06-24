package studio.startapps.pandemona.ondutydrugstores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface OnDutyDrugstoresService {

    Page<OnDutyDrugstores> findAll(Pageable pageable);

    void save(OnDutyDrugstores onDutyDrugstores);

    Optional<OnDutyDrugstores> findById(long onDutyDrugstoresId);

    void deleteById(long onDutyDrugstoresId);

    List<OnDutyDrugstores> findByUpdatedAtGreaterThanEqual(LocalDateTime lastUpdateDate);

    Iterable<OnDutyDrugstores> findAllDeleted();
}
