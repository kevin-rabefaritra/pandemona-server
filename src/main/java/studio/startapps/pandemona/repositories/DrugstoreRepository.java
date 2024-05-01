package studio.startapps.pandemona.repositories;

import org.springframework.data.repository.ListCrudRepository;
import studio.startapps.pandemona.models.Business;
import studio.startapps.pandemona.models.Drugstore;


public interface DrugstoreRepository extends ListCrudRepository<Drugstore, Long> {

    Drugstore findById(long id);
}
