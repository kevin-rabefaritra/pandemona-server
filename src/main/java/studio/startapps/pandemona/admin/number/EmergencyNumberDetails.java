package studio.startapps.pandemona.admin.number;

import studio.startapps.pandemona.admin.city.internal.CityEnum;
import studio.startapps.pandemona.admin.number.internal.EmergencyNumber;
import studio.startapps.pandemona.admin.number.internal.EmergencyNumberType;

import java.util.List;

public record EmergencyNumberDetails(
    String name,
    String address,
    EmergencyNumberType type,
    CityEnum city,
    Float latitude,
    Float longitude,
    List<String> contacts
) {
    public EmergencyNumberDetails(EmergencyNumber emergencyNumber) {
        this(
            emergencyNumber.getName(),
            emergencyNumber.getAddress(),
            emergencyNumber.getType(),
            emergencyNumber.getCity(),
            emergencyNumber.getLatitude(),
            emergencyNumber.getLongitude(),
            emergencyNumber.getContacts()
        );
    }
}
