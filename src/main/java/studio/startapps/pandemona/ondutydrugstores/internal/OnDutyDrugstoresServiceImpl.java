package studio.startapps.pandemona.ondutydrugstores.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstores;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstoresRepository;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstoresService;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OnDutyDrugstoresServiceImpl implements OnDutyDrugstoresService {

    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;

    public OnDutyDrugstoresServiceImpl(OnDutyDrugstoresRepository onDutyDrugstoresRepository) {
        this.onDutyDrugstoresRepository = onDutyDrugstoresRepository;
    }

    @Override
    public Page<OnDutyDrugstores> findAll(Pageable pageable) {
        return this.onDutyDrugstoresRepository.findAll(pageable);
    }

    @Override
    public void save(OnDutyDrugstores onDutyDrugstores) {
        this.onDutyDrugstoresRepository.save(onDutyDrugstores);
    }

    @Override
    public Optional<OnDutyDrugstores> findById(long onDutyDrugstoresId) {
        return this.onDutyDrugstoresRepository.findById(onDutyDrugstoresId);
    }

    @Override
    public void deleteById(long onDutyDrugstoresId) {
        this.onDutyDrugstoresRepository.deleteById(onDutyDrugstoresId);
    }

    @Override
    public List<OnDutyDrugstores> findByUpdatedAtGreaterThanEqual(LocalDateTime lastUpdateDate) {
        return this.onDutyDrugstoresRepository.findByUpdatedAtGreaterThanEqual(lastUpdateDate);
    }

    @Override
    public Iterable<OnDutyDrugstores> findAllDeleted() {
        return this.onDutyDrugstoresRepository.findAllDeleted();
    }

    @Override
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
}
