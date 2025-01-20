package studio.startapps.pandemona.healthcenter.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthCenterMobileService {

    private final HealthCenterRepository healthCenterRepository;

    List<HealthCenterItem> findAll() {
        List<HealthCenterItem> result = new ArrayList<>();
        this.healthCenterRepository.findAll().forEach(item -> {
            result.add(new HealthCenterItem(item));
        });
        return result;
    }
}
