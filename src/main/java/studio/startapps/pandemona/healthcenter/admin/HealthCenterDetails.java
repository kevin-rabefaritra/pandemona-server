package studio.startapps.pandemona.healthcenter.admin;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.healthcenter.internal.HealthCenter;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterType;

import java.util.List;

public record HealthCenterDetails(
    String name,
    String address,
    HealthCenterType type,
    CityEnum city,
    float latitude,
    float longitude,
    List<String> contacts
) {

    public HealthCenterDetails(HealthCenter healthCenter) {
        this(
            healthCenter.getName(),
            healthCenter.getAddress(),
            healthCenter.getType(),
            healthCenter.getCity(),
            healthCenter.getLatitude(),
            healthCenter.getLongitude(),
            healthCenter.getContacts()
        );
    }
}
