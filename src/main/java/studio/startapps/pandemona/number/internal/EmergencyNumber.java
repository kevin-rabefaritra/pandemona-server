package studio.startapps.pandemona.number.internal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import studio.startapps.pandemona.business.Business;

@Entity
@Table(name = "emergency_number")
@NoArgsConstructor @AllArgsConstructor @Data @SuperBuilder
public class EmergencyNumber extends Business {

    @Enumerated(EnumType.STRING)
    private EmergencyNumberType type;
}
