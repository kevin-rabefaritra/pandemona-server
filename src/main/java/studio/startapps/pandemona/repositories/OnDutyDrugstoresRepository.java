package studio.startapps.pandemona.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.startapps.pandemona.models.OnDutyDrugstores;

import java.util.List;

@Repository
public interface OnDutyDrugstoresRepository extends CrudRepository<OnDutyDrugstores, Long> {

    @Query(value = "SELECT * FROM ONDUTYDRUGSTORES WHERE startDate <= ?1 AND endDate >= ?1", nativeQuery = true)
    List<OnDutyDrugstores> findBetweenStartDateAndEndDate(String date);
}
