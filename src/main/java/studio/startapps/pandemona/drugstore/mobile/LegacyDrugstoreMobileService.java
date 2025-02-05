package studio.startapps.pandemona.drugstore.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.startapps.pandemona.city.internal.CityEnum;
import studio.startapps.pandemona.drugstore.internal.Drugstore;
import studio.startapps.pandemona.drugstore.internal.DrugstoreRepository;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstores;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service  @RequiredArgsConstructor
@Deprecated
public class LegacyDrugstoreMobileService {

    private final OnDutyDrugstoresRepository onDutyDrugstoresRepository;
    private final DrugstoreRepository drugstoreRepository;

    Iterable<Drugstore> findDrugstoresByUpdatedAtGreaterThanEqual(LocalDateTime dateTime) {
        List<Drugstore> drugstores = this.drugstoreRepository.findByUpdatedAtGreaterThanEqual(dateTime);

        // Format phone number for legacy client
        return drugstores.stream().peek((drugstore) -> {
            drugstore.setContacts(drugstore.getContacts().stream().map(this::formatContact).toList());
        }).toList();
    }

    Iterable<OnDutyDrugstores> findOnDutyDrugstoresByUpdatedAtGreaterThanEqual(LocalDateTime dateTime, CityEnum city) {
        List<OnDutyDrugstores> onDutyDrugstores = this.onDutyDrugstoresRepository.findByUpdatedAtGreaterThanEqual(dateTime);
        return onDutyDrugstores.stream().peek((item) -> {
            List<Drugstore> drugstores = item.getDrugstores().stream().filter((drugstore) -> {
                return drugstore.getCity().equals(city);
            }).toList();
            item.setDrugstores(new HashSet<>(drugstores));
        }).filter((item) -> !item.getDrugstores().isEmpty()).toList();
    }

    Iterable<Drugstore> findAllDeletedDrugstores() {
        return this.drugstoreRepository.findAllDeleted();
    }

    Iterable<OnDutyDrugstores> findAllDeletedOnDutyDrugstores() {
        return this.onDutyDrugstoresRepository.findAllDeleted();
    }

    private String formatContact(String contact) {
        if (contact.contains("@")) {
            // Email address
            return contact;
        }

        String reducedContact = contact.replaceAll(" ", "");
        if (reducedContact.length() == 10) {
            return String.format("%s %s %s %s", reducedContact.substring(0, 3), reducedContact.substring(3, 5), reducedContact.substring(5, 8), reducedContact.substring(8));
        }
        else if (reducedContact.length() == 7) {
            return String.format("%s %s %s", reducedContact.substring(0, 2), reducedContact.substring(2, 5), reducedContact.substring(5, 7));
        }
        return contact;
    }
}
