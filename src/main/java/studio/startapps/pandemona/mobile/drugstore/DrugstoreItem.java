package studio.startapps.pandemona.mobile.drugstore;

import studio.startapps.pandemona.admin.drugstore.internal.Drugstore;

import java.util.List;

public record DrugstoreItem(
    String id,
    String name,
    String address,
    String city,
    List<String> contacts,
    List<String> features,
    Float latitude,
    Float longitude
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
