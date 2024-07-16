package studio.startapps.pandemona.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.repository.OnDutyDrugstoresRepository;
import studio.startapps.pandemona.repository.dto.OnDutyDrugstoresDTO;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OnDutyDrugstoresService {

    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;

    public OnDutyDrugstoresService(OnDutyDrugstoresRepository onDutyDrugstoresRepository) {
        this.onDutyDrugstoresRepository = onDutyDrugstoresRepository;
    }

    public Page<OnDutyDrugstores> findAll(Pageable pageable) {
        return this.onDutyDrugstoresRepository.findAll(pageable);
    }

    public Page<OnDutyDrugstoresDTO> findAllAsDTO(Pageable pageable) {
        return this.findAll(pageable).map(OnDutyDrugstoresDTO::new);
    }

    public OnDutyDrugstores save(OnDutyDrugstores onDutyDrugstores) {
        return this.onDutyDrugstoresRepository.save(onDutyDrugstores);
    }

    public Optional<OnDutyDrugstores> findById(long onDutyDrugstoresId) {
        return this.onDutyDrugstoresRepository.findById(onDutyDrugstoresId);
    }

    public void deleteById(long onDutyDrugstoresId) {
        this.onDutyDrugstoresRepository.deleteById(onDutyDrugstoresId);
    }

    public List<OnDutyDrugstores> findByUpdatedAtGreaterThanEqual(LocalDateTime lastUpdateDate) {
        return this.onDutyDrugstoresRepository.findByUpdatedAtGreaterThanEqual(lastUpdateDate);
    }

    public Iterable<OnDutyDrugstores> findAllDeleted() {
        return this.onDutyDrugstoresRepository.findAllDeleted();
    }

    public LocalDate getNextStartDate() {
        OnDutyDrugstores onDutyDrugstores = this.onDutyDrugstoresRepository.findLatestOnDutyDrugstores();
        if (onDutyDrugstores != null) {
            return onDutyDrugstores.getEndDate();
        }
        else {
            LocalDate today = LocalDate.now();
            return DateUtils.nextSaturdayAfter(today);
        }
    }

    public List<LocalDate> getNextPeriod() {
        LocalDate startDate = this.getNextStartDate();
        LocalDate endDate = startDate.plusDays(7);
        return List.of(startDate, endDate);
    }
}
