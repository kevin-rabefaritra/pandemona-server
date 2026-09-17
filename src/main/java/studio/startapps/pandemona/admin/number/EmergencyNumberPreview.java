package studio.startapps.pandemona.admin.number;

import studio.startapps.pandemona.admin.city.internal.CityEnum;
import studio.startapps.pandemona.admin.number.internal.EmergencyNumber;
import studio.startapps.pandemona.admin.number.internal.EmergencyNumberType;

import java.util.List;

public record EmergencyNumberPreview(
    long id,
    String name,
    List<String> contacts,
    CityEnum city,
    EmergencyNumberType type
) {

    public EmergencyNumberPreview(EmergencyNumber emergencyNumber) {
        this(
            emergencyNumber.getId(),
            emergencyNumber.getName(),
            emergencyNumber.getContacts(),
            emergencyNumber.getCity(),
            emergencyNumber.getType()
        );
    }
}
