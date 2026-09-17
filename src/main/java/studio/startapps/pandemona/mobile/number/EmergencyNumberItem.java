package studio.startapps.pandemona.mobile.number;

import studio.startapps.pandemona.admin.city.internal.CityEnum;
import studio.startapps.pandemona.admin.number.internal.EmergencyNumber;
import studio.startapps.pandemona.admin.number.internal.EmergencyNumberType;

import java.util.List;

public record EmergencyNumberItem(
    String id,
    String name,
    String address,
    CityEnum city,
    List<String> contacts,
    Float latitude,
    Float longitude,
    EmergencyNumberType type
) {
    public EmergencyNumberItem(EmergencyNumber emergencyNumber) {
        this(
            Long.toString(emergencyNumber.getId()),
            emergencyNumber.getName(),
            emergencyNumber.getAddress(),
            emergencyNumber.getCity(),
            emergencyNumber.getContacts(),
            emergencyNumber.getLatitude(),
            emergencyNumber.getLongitude(),
            emergencyNumber.getType()
        );
    }
}
