package studio.startapps.pandemona.admin.healthcenter;

import studio.startapps.pandemona.admin.city.internal.CityEnum;
import studio.startapps.pandemona.admin.healthcenter.internal.HealthCenter;
import studio.startapps.pandemona.admin.healthcenter.internal.HealthCenterType;

public record HealthCenterPreview(
    long id,
    String name,
    String address,
    CityEnum city,
    HealthCenterType type
) {

    public HealthCenterPreview(HealthCenter healthCenter) {
        this(
            healthCenter.getId(),
            healthCenter.getName(),
            healthCenter.getAddress(),
            healthCenter.getCity(),
            healthCenter.getType()
        );
    }
}
