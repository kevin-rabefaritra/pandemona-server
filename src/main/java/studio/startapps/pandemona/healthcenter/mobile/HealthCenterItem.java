package studio.startapps.pandemona.healthcenter.mobile;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.healthcenter.internal.HealthCenter;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterType;

import java.util.List;

public record HealthCenterItem(
    String id,
    String name,
    String address,
    CityEnum city,
    List<String> contacts,
    float latitude,
    float longitude,
    HealthCenterType type
) {
    public HealthCenterItem(HealthCenter healthCenter) {
        this(
            Long.toString(healthCenter.getId()),
            healthCenter.getName(),
            healthCenter.getAddress(),
            healthCenter.getCity(),
            healthCenter.getContacts(),
            healthCenter.getLatitude(),
            healthCenter.getLongitude(),
            healthCenter.getType()
        );
    }
}
