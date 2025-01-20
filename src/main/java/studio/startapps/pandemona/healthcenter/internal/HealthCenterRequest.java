package studio.startapps.pandemona.healthcenter.internal;

import studio.startapps.pandemona.city.internal.CityEnum;

import java.util.List;

public record HealthCenterRequest(
    String name,
    String address,
    List<String> contacts,
    CityEnum city,
    float latitude,
    float longitude,
    HealthCenterType type
) {
}
