package studio.startapps.pandemona.healthcenter.internal;

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
@Table(name = "health_center")
@NoArgsConstructor @AllArgsConstructor @Data
@SuperBuilder
public class HealthCenter extends Business {

    @Enumerated(EnumType.STRING)
    HealthCenterType type;
}
