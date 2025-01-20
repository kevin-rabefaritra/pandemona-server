package studio.startapps.pandemona.drugstore.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.drugstore.internal.DrugstoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugstoreMobileService {

    private final DrugstoreRepository drugstoreRepository;

    List<DrugstoreItem> findAll() {
        return this.drugstoreRepository.findAll().stream().map(DrugstoreItem::new).toList();
    }
}
