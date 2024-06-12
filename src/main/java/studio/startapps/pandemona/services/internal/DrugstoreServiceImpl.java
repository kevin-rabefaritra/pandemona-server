package studio.startapps.pandemona.services.internal;

import jakarta.persistence.EntityNotFoundException;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.repositories.DrugstoreRepository;
import studio.startapps.pandemona.services.DrugstoreService;

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
}
