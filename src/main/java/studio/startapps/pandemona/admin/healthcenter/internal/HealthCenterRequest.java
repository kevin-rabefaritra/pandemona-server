package studio.startapps.pandemona.admin.healthcenter.internal;

import studio.startapps.pandemona.admin.city.internal.CityEnum;

import java.util.List;

public record HealthCenterRequest(
    String name,
    String address,
    List<String> contacts,
    CityEnum city,
    Float latitude,
    Float longitude,
    HealthCenterType type
) {
}
