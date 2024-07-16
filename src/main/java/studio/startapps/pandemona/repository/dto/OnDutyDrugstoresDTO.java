package studio.startapps.pandemona.repository.dto;

import studio.startapps.pandemona.repository.entity.Business;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;

import java.time.LocalDate;
import java.util.List;

public record OnDutyDrugstoresDTO(
    long id,
    LocalDate startDate,
    LocalDate endDate,
    List<OnDutyDrugstoresItemDTO> drugstores
) {
    public OnDutyDrugstoresDTO(OnDutyDrugstores onDutyDrugstores) {
        this(onDutyDrugstores.getId(),
            onDutyDrugstores.getStartDate(),
            onDutyDrugstores.getEndDate(),
            onDutyDrugstores.getDrugstores().stream().map((item) -> new OnDutyDrugstoresItemDTO(item.getId(), item.getName())).toList()
        );
    }
}
