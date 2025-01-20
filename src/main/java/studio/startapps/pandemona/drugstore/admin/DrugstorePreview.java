package studio.startapps.pandemona.drugstore.admin;

import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.drugstore.internal.Drugstore;

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
