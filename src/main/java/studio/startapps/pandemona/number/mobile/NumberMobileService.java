package studio.startapps.pandemona.number.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.number.internal.NumberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NumberMobileService {

    private final NumberRepository numberRepository;

    List<EmergencyNumberItem> findAll() {
        List<EmergencyNumberItem> result = new ArrayList<>();
        this.numberRepository.findAll().forEach((item) -> {
            result.add(new EmergencyNumberItem(item));
        });
        return result;
    }
}
