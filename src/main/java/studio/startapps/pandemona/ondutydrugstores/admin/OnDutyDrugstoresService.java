package studio.startapps.pandemona.ondutydrugstores.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.drugstore.internal.Drugstore;
import studio.startapps.pandemona.drugstore.internal.DrugstoreRepository;
import studio.startapps.pandemona.ondutydrugstores.exception.OnDutyDrugstoresNotFoundException;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstores;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresDetails;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresPreview;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresRepository;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service @RequiredArgsConstructor
public class OnDutyDrugstoresService {

    private final DrugstoreRepository drugstoreRepository;
    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;

    public Page<OnDutyDrugstoresPreview> findAll(Pageable pageable) {
        return this.onDutyDrugstoresRepository.findAll(pageable).map(OnDutyDrugstoresPreview::new);
    }

    void save(OnDutyDrugstoresRequest request) {
        Set<Drugstore> drugstores = this.drugstoreRepository.findByIdIn(request.drugstores());

        OnDutyDrugstores onDutyDrugstores = OnDutyDrugstores.builder()
                .startDate(request.startDate())
                .endDate(request.endDate())
                .drugstores(drugstores)
                .build();

        this.onDutyDrugstoresRepository.save(onDutyDrugstores);
    }

    void update(long id, OnDutyDrugstoresRequest request) throws OnDutyDrugstoresNotFoundException {
        OnDutyDrugstores onDutyDrugstores = this.onDutyDrugstoresRepository.findById(id)
                .orElseThrow(() -> new OnDutyDrugstoresNotFoundException(id));

        Set<Drugstore> drugstores = this.drugstoreRepository.findByIdIn(request.drugstores());
        onDutyDrugstores.setStartDate(request.startDate());
        onDutyDrugstores.setEndDate(request.endDate());
        onDutyDrugstores.setDrugstores(drugstores);

        this.onDutyDrugstoresRepository.save(onDutyDrugstores);
    }

    public OnDutyDrugstoresDetails findById(long id) throws OnDutyDrugstoresNotFoundException {
        OnDutyDrugstores onDutyDrugstores = this.onDutyDrugstoresRepository.findById(id)
            .orElseThrow(() -> new OnDutyDrugstoresNotFoundException(id));

        return new OnDutyDrugstoresDetails(onDutyDrugstores);
    }

    public void deleteById(long onDutyDrugstoresId) {
        this.onDutyDrugstoresRepository.deleteById(onDutyDrugstoresId);
    }

    public List<LocalDate> getNextPeriod() {
        LocalDate today = DateUtils.today();
        LocalDate startDate = DateUtils.nextSaturdayAfter(today);
        OnDutyDrugstores onDutyDrugstores = this.onDutyDrugstoresRepository.getLastOnDutyDrugstores();

        if (onDutyDrugstores != null && onDutyDrugstores.getStartDate().isAfter(today)) {
            startDate = onDutyDrugstores.getEndDate();
        }

        LocalDate endDate = startDate.plusDays(7);
        return List.of(startDate, endDate);
    }
}
