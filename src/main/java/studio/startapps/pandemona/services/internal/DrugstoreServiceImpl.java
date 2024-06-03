package studio.startapps.pandemona.services.internal;

import studio.startapps.pandemona.repositories.DrugstoreRepository;
import studio.startapps.pandemona.services.DrugstoreService;

public class DrugstoreServiceImpl implements DrugstoreService {

    private DrugstoreRepository drugstoreRepository;

    public DrugstoreServiceImpl(DrugstoreRepository drugstoreRepository) {
        this.drugstoreRepository = drugstoreRepository;
    }
}
