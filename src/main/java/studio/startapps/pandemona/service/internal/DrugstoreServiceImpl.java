package studio.startapps.pandemona.service.internal;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.repository.entity.Drugstore;
import studio.startapps.pandemona.repository.DrugstoreRepository;
import studio.startapps.pandemona.service.DrugstoreService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DrugstoreServiceImpl implements DrugstoreService {

    private DrugstoreRepository drugstoreRepository;

    public DrugstoreServiceImpl(DrugstoreRepository drugstoreRepository) {
        this.drugstoreRepository = drugstoreRepository;
    }

    @Override
    public List<Drugstore> findAll() {
        return this.drugstoreRepository.findAll();
    }

    @Override
    public Page<Drugstore> findAll(Pageable pageable) {
        return this.drugstoreRepository.findAll(pageable);
    }

    public Drugstore save(Drugstore drugstore) {
        return this.drugstoreRepository.save(drugstore);
    }

    public List<Drugstore> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime) {
        return this.drugstoreRepository.findByUpdatedAtGreaterThanEqual(localDateTime);
    }

    public List<Drugstore> findAllDeleted() {
        return this.drugstoreRepository.findAllDeleted();
    }

    @Override
    public Optional<Drugstore> findFirstById(long drugstoreId) {
        return this.drugstoreRepository.findById(drugstoreId);
    }

    @Override
    public void deleteById(long drugstoreId) {
        this.drugstoreRepository.deleteById(drugstoreId);
    }
}
