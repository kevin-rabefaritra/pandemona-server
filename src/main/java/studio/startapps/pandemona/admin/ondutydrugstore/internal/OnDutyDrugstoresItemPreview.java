package studio.startapps.pandemona.admin.ondutydrugstore.internal;

import studio.startapps.pandemona.admin.drugstore.internal.Drugstore;

public record OnDutyDrugstoresItemPreview(
    long id,
    String name
) {

    public OnDutyDrugstoresItemPreview(Drugstore drugstore) {
        this(drugstore.getId(), drugstore.getName());
    }
}
