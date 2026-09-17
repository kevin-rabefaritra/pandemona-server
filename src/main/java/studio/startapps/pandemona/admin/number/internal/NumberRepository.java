package studio.startapps.pandemona.admin.number.internal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NumberRepository extends PagingAndSortingRepository<EmergencyNumber, Long>, CrudRepository<EmergencyNumber, Long> {

}
