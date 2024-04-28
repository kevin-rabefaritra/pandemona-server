package studio.startapps.pandemona.repositories;

import org.springframework.data.repository.ListCrudRepository;
import studio.startapps.pandemona.models.Drugstore;

import java.util.List;


public interface DrugstoreRepository extends ListCrudRepository<Drugstore, Long> {

    Drugstore findById(long id);
}
