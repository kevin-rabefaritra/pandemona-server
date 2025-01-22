package studio.startapps.pandemona.drugstore.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.business.exception.BusinessNotFoundException;
import studio.startapps.pandemona.drugstore.internal.Drugstore;
import studio.startapps.pandemona.drugstore.internal.DrugstoreFeature;
import studio.startapps.pandemona.drugstore.internal.DrugstoreRepository;
import studio.startapps.pandemona.drugstore.internal.DrugstoreSpecification;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresItemPreview;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrugstoreService {

    private final DrugstoreRepository drugstoreRepository;

    Page<DrugstorePreview> findAll(Pageable pageable, Map<String, String> filters) {
        DrugstoreSpecification specification = new DrugstoreSpecification(filters);
        return this.drugstoreRepository.findAll(specification, pageable).map(DrugstorePreview::new);
    }

    List<DrugstorePreview> findByKeyword(String keyword) {
        Pageable pageable = Pageable.ofSize(10);
        return this.drugstoreRepository.findByKeyword(keyword, pageable).map(DrugstorePreview::new).toList();
    }

    List<String> getFeatures() {
        return Arrays.stream(DrugstoreFeature.values()).map(DrugstoreFeature::name).toList();
    }

    void save(DrugstoreRequest request) {
        Drugstore drugstore = Drugstore.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .features(request.features())
                .contacts(request.contacts())
                .build();
        this.drugstoreRepository.save(drugstore);
    }

    void update(long id, DrugstoreRequest request) throws BusinessNotFoundException {
        Drugstore drugstore = this.drugstoreRepository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException("Drugstore not found"));

        drugstore.setName(request.name());
        drugstore.setAddress(request.address());
        drugstore.setContacts(request.contacts());
        drugstore.setCity(request.city());
        drugstore.setLatitude(request.latitude());
        drugstore.setLongitude(request.longitude());
        drugstore.setFeatures(request.features());
        this.drugstoreRepository.save(drugstore);
    }

    public DrugstoreDetails findById(long drugstoreId) throws BusinessNotFoundException {
        Drugstore drugstore = this.drugstoreRepository.findById(drugstoreId)
                .orElseThrow(() -> new BusinessNotFoundException("Drugstore not found"));

        return new DrugstoreDetails(drugstore);
    }

    public void deleteById(long drugstoreId) {
        this.drugstoreRepository.deleteById(drugstoreId);
    }
}
