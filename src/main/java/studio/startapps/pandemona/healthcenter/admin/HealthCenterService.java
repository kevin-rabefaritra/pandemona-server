package studio.startapps.pandemona.healthcenter.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.business.exception.BusinessNotFoundException;
import studio.startapps.pandemona.healthcenter.internal.HealthCenter;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterRepository;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterRequest;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterType;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthCenterService {

    private final HealthCenterRepository healthCenterRepository;

    Page<HealthCenterPreview> findAll(Pageable pageable) {
        return this.healthCenterRepository.findAll(pageable).map(HealthCenterPreview::new);
    }

    HealthCenterDetails findById(long id) throws BusinessNotFoundException {
        HealthCenter healthCenter = this.healthCenterRepository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException("Health center not found"));

        return new HealthCenterDetails(healthCenter);
    }

    void save(HealthCenterRequest request) {
        HealthCenter healthCenter = HealthCenter.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .contacts(request.contacts())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .type(request.type())
                .build();
        this.healthCenterRepository.save(healthCenter);
    }

    void update(long id, HealthCenterRequest request) throws BusinessNotFoundException {
        HealthCenter healthCenter = this.healthCenterRepository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException("Health center not found"));

        healthCenter.setName(request.name());
        healthCenter.setAddress(request.address());
        healthCenter.setCity(request.city());
        healthCenter.setContacts(request.contacts());
        healthCenter.setLatitude(request.latitude());
        healthCenter.setLongitude(request.longitude());
        healthCenter.setType(request.type());
        this.healthCenterRepository.save(healthCenter);
    }

    void delete(long id) throws BusinessNotFoundException {
        this.healthCenterRepository.findById(id).orElseThrow(() -> new BusinessNotFoundException("Emergency number not found"));
        this.healthCenterRepository.deleteById(id);
    }

    List<String> getTypes() {
        return Arrays.stream(HealthCenterType.values()).map(HealthCenterType::name).toList();
    }

}
