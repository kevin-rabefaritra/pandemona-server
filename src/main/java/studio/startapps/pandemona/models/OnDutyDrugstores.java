package studio.startapps.pandemona.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class OnDutyDrugstores extends BaseEntity {

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Drugstore> drugstores;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Drugstore> getDrugstores() {
        return drugstores;
    }

    public void setDrugstores(Set<Drugstore> drugstores) {
        this.drugstores = drugstores;
    }
}
