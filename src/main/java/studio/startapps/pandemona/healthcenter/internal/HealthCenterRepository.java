package studio.startapps.pandemona.healthcenter.internal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HealthCenterRepository extends PagingAndSortingRepository<HealthCenter, Long>, CrudRepository<HealthCenter, Long> {

}
