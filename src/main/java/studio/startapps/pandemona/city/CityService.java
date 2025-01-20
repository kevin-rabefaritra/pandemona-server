package studio.startapps.pandemona.city;

import org.springframework.stereotype.Service;
import studio.startapps.pandemona.city.internal.CityEnum;

import java.util.Arrays;
import java.util.List;

@Service
public class CityService {

    List<String> getCities() {
        return Arrays.stream(CityEnum.values()).map(CityEnum::name).sorted().toList();
    }
}
