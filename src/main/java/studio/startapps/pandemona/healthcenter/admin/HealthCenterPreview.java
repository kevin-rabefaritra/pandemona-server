package studio.startapps.pandemona.healthcenter.admin;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.healthcenter.internal.HealthCenter;
import studio.startapps.pandemona.healthcenter.internal.HealthCenterType;

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
