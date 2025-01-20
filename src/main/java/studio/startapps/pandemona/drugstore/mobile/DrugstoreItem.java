package studio.startapps.pandemona.drugstore.mobile;

import studio.startapps.pandemona.drugstore.internal.Drugstore;

import java.util.List;

public record DrugstoreItem(
    String id,
    String name,
    String address,
    String city,
    List<String> contacts,
    List<String> features,
    float latitude,
    float longitude
) {
    public DrugstoreItem(Drugstore drugstore) {
        this(
            Long.toString(drugstore.getId()),
            drugstore.getName(),
            drugstore.getAddress(),
            drugstore.getCity().name().toLowerCase(),
            drugstore.getContacts(),
            drugstore.getFeatures(),
            drugstore.getLatitude(),
            drugstore.getLongitude()
        );
    }
}
