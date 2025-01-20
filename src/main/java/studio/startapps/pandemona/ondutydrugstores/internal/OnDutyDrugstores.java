package studio.startapps.pandemona.ondutydrugstores.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import studio.startapps.pandemona.drugstore.internal.Drugstore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * On-Duty drugstores
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "onduty_drugstores")
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class OnDutyDrugstores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(
        name = "onduty_drugstores_drugstore",
        joinColumns = @JoinColumn(name = "onduty_drugstores_id"),
        inverseJoinColumns = @JoinColumn(name = "drugstore_id")
    )
    private Set<Drugstore> drugstores;

    @Transient
    public List<Long> drugstoreIds;
}
