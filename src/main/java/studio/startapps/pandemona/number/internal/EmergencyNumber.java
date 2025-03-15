package studio.startapps.pandemona.number.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import studio.startapps.pandemona.business.Business;

@Entity
@Table(name = "emergency_number")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor @AllArgsConstructor @Data @SuperBuilder
public class EmergencyNumber extends Business {

    @Enumerated(EnumType.STRING)
    private EmergencyNumberType type;
}
