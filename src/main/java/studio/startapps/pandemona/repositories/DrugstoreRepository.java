package studio.startapps.pandemona.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import studio.startapps.pandemona.models.Drugstore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface DrugstoreRepository extends ListPagingAndSortingRepository<Drugstore, Long>, CrudRepository<Drugstore, Long> {

    Drugstore findFirstById(long id);

    Set<Drugstore> findByIdIn(List<Long> ids);

    Set<Drugstore> findByCreatedDateGreaterThanEqual(LocalDateTime localDateTime);
}
