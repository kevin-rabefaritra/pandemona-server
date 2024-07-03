package studio.startapps.pandemona.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.startapps.pandemona.repository.entity.Drugstore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DrugstoreService {

    List<Drugstore> findAll();

    Page<Drugstore> findAll(Pageable pageable);

    Drugstore save(Drugstore drugstore);

    List<Drugstore> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime);

    List<Drugstore> findAllDeleted();

    Optional<Drugstore> findFirstById(long drugstoreId);

    void deleteById(long drugstoreId);
}
