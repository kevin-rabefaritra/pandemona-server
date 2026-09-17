package studio.startapps.pandemona.admin.number.internal;

import studio.startapps.pandemona.admin.city.internal.CityEnum;

import java.util.List;

public record EmergencyNumberRequest(
    String name,
    String address,
    List<String> contacts,
    CityEnum city,
    Float latitude,
    Float longitude,
    EmergencyNumberType type
) {
}
