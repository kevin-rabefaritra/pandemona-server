package studio.startapps.pandemona.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * On-Duty drugstores
 */
@Entity
@Table(name = "onduty_drugstores")
public class OnDutyDrugstores extends BaseEntity {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany
    @NotEmpty
    @JoinTable(
        name = "onduty_drugstores_drugstore",
        joinColumns = @JoinColumn(name = "onduty_drugstores_id"),
        inverseJoinColumns = @JoinColumn(name = "drugstore_id")
    )
    private Set<Drugstore> drugstores;

    @Transient
    public List<Long> drugstoreIds;

    public OnDutyDrugstores(Long id, LocalDate startDate, LocalDate endDate, Set<Drugstore> drugstores) {
        this.setId(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.drugstores = drugstores;
    }

    public OnDutyDrugstores(Long id, LocalDate startDate, LocalDate endDate, List<Long> drugstoresIds) {
        this.setId(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.drugstoreIds = drugstoresIds;
    }

    public OnDutyDrugstores() {

    }

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

    public void addDrugstore(Drugstore drugstore) {
        this.drugstores.add(drugstore);
    }
}
