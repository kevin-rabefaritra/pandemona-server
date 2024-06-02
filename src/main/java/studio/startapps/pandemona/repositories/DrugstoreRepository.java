package studio.startapps.pandemona.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.startapps.pandemona.models.Drugstore;

@Repository
public interface DrugstoreRepository extends CrudRepository<Drugstore, Long> {

    Drugstore findFirstById(long id);
}
