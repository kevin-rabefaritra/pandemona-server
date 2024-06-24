package studio.startapps.pandemona.drugstore.internal;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import studio.startapps.pandemona.drugstore.Drugstore;
import studio.startapps.pandemona.drugstore.DrugstoreRepository;
import studio.startapps.pandemona.drugstore.DrugstoreService;

import java.time.LocalDateTime;
import java.util.List;

public class DrugstoreServiceImpl implements DrugstoreService {

    private DrugstoreRepository drugstoreRepository;

    public DrugstoreServiceImpl(DrugstoreRepository drugstoreRepository) {
        this.drugstoreRepository = drugstoreRepository;
    }

    @Override
    public void update(long id, Drugstore drugstore) {
        Drugstore drugstore1 = drugstoreRepository.findFirstById(id);
        if (drugstore1 != null) {
            drugstore1.setName(drugstore.getName());
            drugstore1.setContacts(drugstore.getContacts());
            drugstore1.setCity(drugstore.getCity());
            drugstore1.setAddress(drugstore.getAddress());
            drugstore1.setLatitude(drugstore.getLatitude());
            drugstore1.setLongitude(drugstore.getLongitude());
            drugstoreRepository.save(drugstore1);
        }
        else {
            throw new EntityNotFoundException(String.format("Drugstore with ID %s not found", id));
        }
    }

    @Override
    public List<Drugstore> findAll() {
        return this.drugstoreRepository.findAll();
    }

    @Override
    public Page<Drugstore> findAll(Pageable pageable) {
        return this.drugstoreRepository.findAll(pageable);
    }

    public void save(Drugstore drugstore) {
        this.drugstoreRepository.save(drugstore);
    }

    public List<Drugstore> findByUpdatedAtGreaterThanEqual(LocalDateTime localDateTime) {
        return this.drugstoreRepository.findByUpdatedAtGreaterThanEqual(localDateTime);
    }

    @Override
    public List<Drugstore> findByIdIn(List<Long> ids) {
        return this.drugstoreRepository.findByIdIn(ids);
    }

    public List<Drugstore> findAllDeleted() {
        return this.drugstoreRepository.findAllDeleted();
    }

    @Override
    public Drugstore findFirstById(long drugstoreId) {
        return this.drugstoreRepository.findFirstById(drugstoreId);
    }

    @Override
    public void deleteById(long drugstoreId) {
        this.drugstoreRepository.deleteById(drugstoreId);
    }
}
