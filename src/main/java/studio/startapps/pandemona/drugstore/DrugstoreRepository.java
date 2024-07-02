package studio.startapps.pandemona.drugstore;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DrugstoreRepository extends ListPagingAndSortingRepository<Drugstore, Long>, ListCrudRepository<Drugstore, Long> {

    Drugstore findFirstById(long id);

    List<Drugstore> findByIdIn(List<Long> ids);

    List<Drugstore> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime);

    @Query(value = "SELECT * FROM drugstore WHERE deleted = true", nativeQuery = true)
    List<Drugstore> findAllDeleted();
}
