package studio.startapps.pandemona.drugstore.internal;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import studio.startapps.pandemona.business.Business;
import studio.startapps.pandemona.business.StringListConverter;

import java.util.List;

/**
 * Drugstores
 */
@Entity
@Table(name = "drugstore")
@NoArgsConstructor @SuperBuilder @AllArgsConstructor @Data
public class Drugstore extends Business {

    @Convert(converter = StringListConverter.class)
    private List<String> features;

    /**
     * https://stackoverflow.com/a/71739927
     * HashSet (entirely reasonably) assumes reflexivity, and doesn't check for equality when it finds that the exact
     * same object is already in the set, as an optimization. Therefore, it will not even call your equals method - it
     * considers that the object is already in the set, so doesn't add a second copy.
     * We need to make sure the hashcode and equals are correct
     */
    @Override
    public int hashCode() {
        return Long.hashCode(this.getId());
    }
}
