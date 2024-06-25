package studio.startapps.pandemona.ondutydrugstores;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OnDutyDrugstoresRepository extends CrudRepository<OnDutyDrugstores, Long>, ListPagingAndSortingRepository<OnDutyDrugstores, Long> {

    @Query(value = "SELECT * FROM onduty_drugstores WHERE startDate <= ?1 AND endDate >= ?1", nativeQuery = true)
    List<OnDutyDrugstores> findBetweenStartDateAndEndDate(String date);

    List<OnDutyDrugstores> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime);

    @Query(value = "SELECT * FROM onduty_drugstores WHERE deleted = true", nativeQuery = true)
    List<OnDutyDrugstores> findAllDeleted();
}