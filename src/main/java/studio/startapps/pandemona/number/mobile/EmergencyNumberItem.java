package studio.startapps.pandemona.number.mobile;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.number.internal.EmergencyNumberType;
import studio.startapps.pandemona.number.internal.EmergencyNumber;

import java.util.List;

public record EmergencyNumberItem(
    String name,
    String address,
    CityEnum city,
    List<String> contacts,
    float latitude,
    float longitude,
    EmergencyNumberType type
) {
    public EmergencyNumberItem(EmergencyNumber emergencyNumber) {
        this(
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