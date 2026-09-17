package studio.startapps.pandemona.admin.number.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import studio.startapps.pandemona.admin.business.internal.Business;

@Entity
@Table(name = "emergency_number")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor @AllArgsConstructor @Data @SuperBuilder
public class EmergencyNumber extends Business {

    @Enumerated(EnumType.STRING)
    private EmergencyNumberType type;
}
