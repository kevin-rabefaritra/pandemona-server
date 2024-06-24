package studio.startapps.pandemona.drugstore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DrugstoreService {

    void update(long id, Drugstore drugstore);

    List<Drugstore> findAll();

    Page<Drugstore> findAll(Pageable pageable);

    void save(Drugstore drugstore);

    List<Drugstore> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime);

    List<Drugstore> findByIdIn(List<Long> ids);

    List<Drugstore> findAllDeleted();

    Drugstore findFirstById(long drugstoreId);

    void deleteById(long drugstoreId);
}
