package studio.startapps.pandemona.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OnDutyDrugstoresService {

    Page<OnDutyDrugstores> findAll(Pageable pageable);

    OnDutyDrugstores save(OnDutyDrugstores onDutyDrugstores);

    Optional<OnDutyDrugstores> findById(long onDutyDrugstoresId);

    void deleteById(long onDutyDrugstoresId);

    List<OnDutyDrugstores> findByUpdatedAtGreaterThanEqual(LocalDateTime lastUpdateDate);

    Iterable<OnDutyDrugstores> findAllDeleted();

    LocalDate getNextStartDate();
}