package studio.startapps.pandemona.drugstore;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import studio.startapps.pandemona.drugstore.internal.Business;
import studio.startapps.pandemona.drugstore.internal.CityEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Drugstores
 */
@Entity
@Table(name = "drugstore")
@NoArgsConstructor @Builder
public class Drugstore extends Business {


}
