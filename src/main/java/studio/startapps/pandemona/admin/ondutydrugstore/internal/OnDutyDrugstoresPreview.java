package studio.startapps.pandemona.admin.ondutydrugstore.internal;

import studio.startapps.pandemona.admin.drugstore.internal.Drugstore;

import java.time.LocalDate;
import java.util.List;

public record OnDutyDrugstoresPreview(
    long id,
    LocalDate startDate,
    LocalDate endDate,
    List<String> drugstores
) {
    public OnDutyDrugstoresPreview(OnDutyDrugstores onDutyDrugstores) {
        this(onDutyDrugstores.getId(),
            onDutyDrugstores.getStartDate(),
            onDutyDrugstores.getEndDate(),
            onDutyDrugstores.getDrugstores().stream().map(Drugstore::getName).toList()
        );
    }
}
