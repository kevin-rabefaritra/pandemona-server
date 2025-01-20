package studio.startapps.pandemona.drugstore.admin;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.drugstore.internal.Drugstore;

import java.util.List;

public record DrugstoreRequest(
    String name,
    String address,
    CityEnum city,
    float latitude,
    float longitude,
    List<String> contacts,
    List<String> features
) {
    public DrugstoreRequest(Drugstore drugstore) {
        this(
            drugstore.getName(),
            drugstore.getAddress(),
            drugstore.getCity(),
            drugstore.getLatitude(),
            drugstore.getLongitude(),
            drugstore.getContacts(),
            drugstore.getFeatures()
        );
    }
}
