package studio.startapps.pandemona.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.repository.DrugstoreRepository;
import studio.startapps.pandemona.repository.dto.OnDutyDrugstoresItemDTO;
import studio.startapps.pandemona.repository.entity.Drugstore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DrugstoreService {

    private DrugstoreRepository drugstoreRepository;

    public DrugstoreService(DrugstoreRepository drugstoreRepository) {
        this.drugstoreRepository = drugstoreRepository;
    }

    public List<Drugstore> findAll() {
        return this.drugstoreRepository.findAll();
    }

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

    public Optional<Drugstore> findFirstById(long drugstoreId) {
        return this.drugstoreRepository.findById(drugstoreId);
    }

    public void deleteById(long drugstoreId) {
        this.drugstoreRepository.deleteById(drugstoreId);
    }

    public List<OnDutyDrugstoresItemDTO> findAllAsDTO() {
        return this.findAll().stream().map((item) -> new OnDutyDrugstoresItemDTO(item.getId(), item.getName())).toList();
    }
}
