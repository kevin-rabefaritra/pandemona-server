package studio.startapps.pandemona.drugstore.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.drugstore.Drugstore;
import studio.startapps.pandemona.drugstore.DrugstoreService;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstoresService;
import studio.startapps.pandemona.util.models.BaseEntity;
import studio.startapps.pandemona.ondutydrugstores.OnDutyDrugstores;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v3")
@Deprecated
public class DrugstoreAPIControllerV3 {

    private int appVersion;

    private DrugstoreService drugstoreService;

    private OnDutyDrugstoresService onDutyDrugstoresService;

    public DrugstoreAPIControllerV3(
            @Value("#{environment['pandemonium.android.version']}") int appVersion,
            DrugstoreService drugstoreService,
            OnDutyDrugstoresService onDutyDrugstoresService
    ) {
        this.appVersion = appVersion;
        this.drugstoreService = drugstoreService;
        this.onDutyDrugstoresService = onDutyDrugstoresService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<String>("hello.", HttpStatus.OK);
    }

    @GetMapping("/fetch/pharmada/{lastUpdate}")
    @Deprecated
    public Map<String, Object> fetch(@PathVariable String lastUpdate) {
        Map<String, Object> result = new HashMap<>();

        LocalDateTime lastUpdateDate = LocalDate.parse(lastUpdate).atStartOfDay();
        LocalDate today = LocalDate.now();
        Iterable<Drugstore> drugstores = this.drugstoreService.findByUpdatedAtGreaterThanEqual(lastUpdateDate);
        Iterable<OnDutyDrugstores> onDutyDrugstores = this.onDutyDrugstoresService.findByUpdatedAtGreaterThanEqual(lastUpdateDate);

        // Change the format of drugstores to match legacy version
        List<Map<String, Object>> formattedDrugstores = StreamSupport.stream(drugstores.spliterator(), false)
            .map((item) -> {
                Map<String, Object> mappedItem = new HashMap<>();
                mappedItem.put("id", item.getId());
                mappedItem.put("n", item.getName());
                mappedItem.put("a", item.getAddress());
                mappedItem.put("c", String.join(";", item.getContacts()));
                mappedItem.put("lt", item.getLatitude());
                mappedItem.put("ln", item.getLongitude());
                mappedItem.put("ci", item.getCity().ordinal() + 10); // legacy city value = City ordinal value + 10
                mappedItem.put("o", 0);
                return mappedItem;
            }).toList();

        // Change the format of all-night drugstores to match legacy version
        List<Map<String, Object>> formattedOnDutyDrugstores = StreamSupport.stream(onDutyDrugstores.spliterator(), false)
            .map((item) -> {
                Map<String, Object> mappedItem = new HashMap<>();
                mappedItem.put("id", item.getId());
                mappedItem.put("s", DateUtils.formatDateISO(item.getStartDate()));
                mappedItem.put("e", DateUtils.formatDateISO(item.getEndDate()));
                mappedItem.put("ct", 10); // we don't use city in OnDutyDrugstores entity anymore
                mappedItem.put("dr", item.getDrugstores().stream().map(BaseEntity::getId).toList());
                return mappedItem;
            }).toList();

        // Deleted drugstores
        Iterable<Drugstore> deletedDrugstores = drugstoreService.findAllDeleted();
        List<Long> deletedDrugstoreIds = StreamSupport.stream(deletedDrugstores.spliterator(), false)
            .map(BaseEntity::getId)
            .toList();

        // Deleted on-duty drugstores
        Iterable<OnDutyDrugstores> deletedOnDutyDrugstores = onDutyDrugstoresService.findAllDeleted();
        List<Long> deletedOnDutyDrugstoreIds = StreamSupport.stream(deletedOnDutyDrugstores.spliterator(), false)
            .map(BaseEntity::getId)
            .toList();

        result.put("cd", DateUtils.formatDateISO(today));
        result.put("version", this.appVersion);
        result.put("dr", formattedDrugstores);
        result.put("an_dr", formattedOnDutyDrugstores);

        result.put("dr_r", deletedDrugstoreIds);
        result.put("an_dr_r", deletedOnDutyDrugstoreIds);
        result.put("en_r", List.of()); // leave empty
        result.put("cl", List.of()); // leave empty
        result.put("cl_r", List.of()); // leave empty

        return result;
    }
}
