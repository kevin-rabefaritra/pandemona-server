package studio.startapps.pandemona.mobile.healthcenter;

import studio.startapps.pandemona.admin.city.internal.CityEnum;
import studio.startapps.pandemona.admin.healthcenter.internal.HealthCenter;
import studio.startapps.pandemona.admin.healthcenter.internal.HealthCenterType;

import java.util.List;

public record HealthCenterItem(
    String id,
    String name,
    String address,
    CityEnum city,
    List<String> contacts,
    Float latitude,
    Float longitude,
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
