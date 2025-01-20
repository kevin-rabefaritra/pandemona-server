package studio.startapps.pandemona.ondutydrugstores.mobile;

import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstores;

import java.time.LocalDate;
import java.util.List;

public record OnDutyDrugstoresItem (
    LocalDate startDate,
    LocalDate endDate,
    List<String> drugstoreIds
) {
    public OnDutyDrugstoresItem(OnDutyDrugstores onDutyDrugstores) {
        this(
            onDutyDrugstores.getStartDate(),
            onDutyDrugstores.getEndDate(),
            onDutyDrugstores.getDrugstores().stream().map((item) -> String.valueOf(item.getId())).toList()
        );
    }
}
