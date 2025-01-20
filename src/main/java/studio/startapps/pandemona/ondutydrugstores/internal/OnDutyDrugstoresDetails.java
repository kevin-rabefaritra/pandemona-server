package studio.startapps.pandemona.ondutydrugstores.internal;

import studio.startapps.pandemona.drugstore.admin.DrugstorePreview;
import studio.startapps.pandemona.drugstore.internal.Drugstore;

import java.time.LocalDate;
import java.util.List;

public record OnDutyDrugstoresDetails(
    long id,
    LocalDate startDate,
    LocalDate endDate,
    List<DrugstorePreview> drugstores
) {
    public OnDutyDrugstoresDetails(OnDutyDrugstores onDutyDrugstores) {
        this(
            onDutyDrugstores.getId(),
            onDutyDrugstores.getStartDate(),
            onDutyDrugstores.getEndDate(),
            onDutyDrugstores.getDrugstores().stream().map(DrugstorePreview::new).toList()
        );
    }
}
