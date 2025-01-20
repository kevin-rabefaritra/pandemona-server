package studio.startapps.pandemona.ondutydrugstores.internal;

import studio.startapps.pandemona.drugstore.internal.Drugstore;

public record OnDutyDrugstoresItemPreview(
    long id,
    String name
) {

    public OnDutyDrugstoresItemPreview(Drugstore drugstore) {
        this(drugstore.getId(), drugstore.getName());
    }
}
