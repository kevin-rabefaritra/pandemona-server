package studio.startapps.pandemona.number.admin;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.number.internal.EmergencyNumberType;
import studio.startapps.pandemona.number.internal.EmergencyNumber;

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
