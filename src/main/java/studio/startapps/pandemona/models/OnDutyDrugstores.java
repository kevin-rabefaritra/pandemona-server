package studio.startapps.pandemona.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * On-Duty drugstores
 */
@Entity
@Table(name = "ONDUTYDRUGSTORES")
public class OnDutyDrugstores extends BaseEntity {

    @Column(name = "STARTDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "ENDDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY)
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
