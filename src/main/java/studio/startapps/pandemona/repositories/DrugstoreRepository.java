package studio.startapps.pandemona.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.startapps.pandemona.models.Drugstore;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface DrugstoreRepository extends CrudRepository<Drugstore, Long> {

    Drugstore findFirstById(long id);

    Set<Drugstore> findByIdIn(List<Long> ids);
}
