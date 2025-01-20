package studio.startapps.pandemona.number.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.business.exception.BusinessNotFoundException;
import studio.startapps.pandemona.number.internal.EmergencyNumber;
import studio.startapps.pandemona.number.internal.EmergencyNumberRequest;
import studio.startapps.pandemona.number.internal.EmergencyNumberType;
import studio.startapps.pandemona.number.internal.NumberRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NumberService {

    private final NumberRepository numberRepository;

    Page<EmergencyNumberPreview> findAll(Pageable pageable) {
        return this.numberRepository.findAll(pageable).map(EmergencyNumberPreview::new);
    }

    EmergencyNumberDetails findById(long id) throws BusinessNotFoundException {
        EmergencyNumber emergencyNumber = this.numberRepository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException("Emergency number not found"));

        return new EmergencyNumberDetails(emergencyNumber);
    }

    List<String> getTypes() {
        return Arrays.stream(EmergencyNumberType.values()).map(EmergencyNumberType::name).toList();
    }

    void save(EmergencyNumberRequest request) {
        EmergencyNumber emergencyNumber = EmergencyNumber.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .contacts(request.contacts())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .type(request.type())
                .build();
        this.numberRepository.save(emergencyNumber);
    }

    void update(long id, EmergencyNumberRequest request) throws BusinessNotFoundException {
        EmergencyNumber emergencyNumber = this.numberRepository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException("Emergency number not found"));

        emergencyNumber.setName(request.name());
        emergencyNumber.setAddress(request.address());
        emergencyNumber.setCity(request.city());
        emergencyNumber.setContacts(request.contacts());
        emergencyNumber.setLatitude(request.latitude());
        emergencyNumber.setLongitude(request.longitude());
        emergencyNumber.setType(request.type());
        this.numberRepository.save(emergencyNumber);
    }

    void delete(long id) throws BusinessNotFoundException {
        this.numberRepository.findById(id).orElseThrow(() -> new BusinessNotFoundException("Emergency number not found"));
        this.numberRepository.deleteById(id);
    }
}
