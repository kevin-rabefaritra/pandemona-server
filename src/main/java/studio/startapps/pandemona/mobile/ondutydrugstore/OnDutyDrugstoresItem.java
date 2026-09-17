package studio.startapps.pandemona.mobile.ondutydrugstore;

import studio.startapps.pandemona.admin.ondutydrugstore.internal.OnDutyDrugstores;

import java.time.LocalDate;
import java.util.List;

public record OnDutyDrugstoresItem (
    String id,
    LocalDate startDate,
    LocalDate endDate,
    List<String> drugstoreIds
) {
    public OnDutyDrugstoresItem(OnDutyDrugstores onDutyDrugstores) {
        this(
            Long.toString(onDutyDrugstores.getId()),
            onDutyDrugstores.getStartDate(),
            onDutyDrugstores.getEndDate(),
            onDutyDrugstores.getDrugstores().stream().map((item) -> String.valueOf(item.getId())).toList()
        );
    }
}
