package studio.startapps.pandemona.ondutydrugstores;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OnDutyDrugstoresRepository extends CrudRepository<OnDutyDrugstores, Long>, ListPagingAndSortingRepository<OnDutyDrugstores, Long> {

    @Query(value = "SELECT o FROM OnDutyDrugstores o WHERE o.startDate <= ?1 AND o.endDate >= ?1")
    List<OnDutyDrugstores> findBetweenStartDateAndEndDate(String date);

    List<OnDutyDrugstores> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime);

    @Query(value = "SELECT o FROM OnDutyDrugstores o")
    List<OnDutyDrugstores> findAllDeleted();

    @Query(value = "SELECT o FROM OnDutyDrugstores o ORDER BY o.endDate DESC LIMIT 1")
    OnDutyDrugstores findLatestOnDutyDrugstores();
}
