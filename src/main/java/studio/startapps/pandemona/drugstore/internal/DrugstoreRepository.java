package studio.startapps.pandemona.drugstore.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface DrugstoreRepository extends ListPagingAndSortingRepository<Drugstore, Long>, ListCrudRepository<Drugstore, Long>, JpaSpecificationExecutor<Drugstore> {

    Set<Drugstore> findByIdIn(List<Long> ids);

    List<Drugstore> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime);

    @Query(value = "SELECT * FROM drugstore WHERE deleted = true", nativeQuery = true)
    List<Drugstore> findAllDeleted();

    @Query("SELECT d FROM Drugstore d WHERE Lower(name) LIKE Concat('%', Lower(?1), '%')")
    Page<Drugstore> findByKeyword(String keyword, Pageable pageable);
}
