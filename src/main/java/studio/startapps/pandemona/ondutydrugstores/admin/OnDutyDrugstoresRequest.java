package studio.startapps.pandemona.ondutydrugstores.admin;

import java.time.LocalDate;
import java.util.List;

public record OnDutyDrugstoresRequest(
    LocalDate startDate,
    LocalDate endDate,
    List<Long> drugstores
) {
}
