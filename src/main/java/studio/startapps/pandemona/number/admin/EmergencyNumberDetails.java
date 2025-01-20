package studio.startapps.pandemona.number.admin;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.number.internal.EmergencyNumberType;
import studio.startapps.pandemona.number.internal.EmergencyNumber;

import java.util.List;

public record EmergencyNumberDetails(
    String name,
    String address,
    EmergencyNumberType type,
    CityEnum city,
    float latitude,
    float longitude,
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
