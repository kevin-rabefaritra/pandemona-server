package studio.startapps.pandemona.ondutydrugstores.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OnDutyDrugstoresRepository extends JpaRepository<OnDutyDrugstores, Long>, JpaSpecificationExecutor<OnDutyDrugstores> {

    List<OnDutyDrugstores> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime);

    @Query(value = "SELECT * FROM onduty_drugstores WHERE deleted = true", nativeQuery = true)
    List<OnDutyDrugstores> findAllDeleted();

    @Query(value = "SELECT o FROM OnDutyDrugstores o ORDER BY o.endDate DESC LIMIT 1")
    OnDutyDrugstores getLastOnDutyDrugstores();

    @Query(value = "SELECT o FROM OnDutyDrugstores o WHERE o.endDate >= ?1")
    Page<OnDutyDrugstores> findAllByEndDateAfter(LocalDate localDate, Pageable pageable);
}
