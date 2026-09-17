package studio.startapps.pandemona.admin.drugstore;

import studio.startapps.pandemona.admin.city.internal.CityEnum;
import studio.startapps.pandemona.admin.drugstore.internal.Drugstore;

public record DrugstorePreview(
    long id,
    String name,
    String address,
    CityEnum city
) {

    public DrugstorePreview(Drugstore drugstore) {
        this(
            drugstore.getId(),
            drugstore.getName(),
            drugstore.getAddress(),
            drugstore.getCity()
        );
    }
}
