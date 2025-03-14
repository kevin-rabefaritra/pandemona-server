package studio.startapps.pandemona.ondutydrugstores.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.drugstore.internal.Drugstore;
import studio.startapps.pandemona.drugstore.internal.DrugstoreRepository;
import studio.startapps.pandemona.ondutydrugstores.exception.OnDutyDrugstoresNotFoundException;
import studio.startapps.pandemona.ondutydrugstores.internal.*;
import studio.startapps.pandemona.util.DataPage;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service @RequiredArgsConstructor
public class OnDutyDrugstoresService {

    private final DrugstoreRepository drugstoreRepository;
    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;

    public DataPage<OnDutyDrugstoresPreview> findAll(Map<String, String> params, Pageable pageable) {
        Specification<OnDutyDrugstores> specification = OnDutyDrugstoresSpecs.withParams(params);
        Page<OnDutyDrugstoresPreview> onDutyDrugstoresPreviews = this.onDutyDrugstoresRepository.findAll(specification, pageable).map(OnDutyDrugstoresPreview::new);
        return new DataPage<>(onDutyDrugstoresPreviews);
    }

    List<OnDutyDrugstoresPreview> findAll(Map<String, String> params) {
        Specification<OnDutyDrugstores> specification = OnDutyDrugstoresSpecs.withParams(params);
        return this.onDutyDrugstoresRepository.findAll(specification).stream().map(OnDutyDrugstoresPreview::new).toList();
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
