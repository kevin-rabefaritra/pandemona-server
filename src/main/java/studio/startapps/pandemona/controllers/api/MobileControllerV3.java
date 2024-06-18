package studio.startapps.pandemona.controllers.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.startapps.pandemona.models.Drugstore;
import studio.startapps.pandemona.models.OnDutyDrugstores;
import studio.startapps.pandemona.repositories.DrugstoreRepository;
import studio.startapps.pandemona.repositories.OnDutyDrugstoresRepository;
import studio.startapps.pandemona.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v3")
public class MobileControllerV3 {

    private int appVersion;

    private DrugstoreRepository drugstoreRepository;

    private OnDutyDrugstoresRepository onDutyDrugstoresRepository;

    public MobileControllerV3(
            @Value("#{environment['pandemonium.android.version']}") int appVersion,
            DrugstoreRepository drugstoreRepository,
            OnDutyDrugstoresRepository onDutyDrugstoresRepository
    ) {
        this.appVersion = appVersion;
        this.drugstoreRepository = drugstoreRepository;
        this.onDutyDrugstoresRepository = onDutyDrugstoresRepository;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<String>("hello.", HttpStatus.OK);
    }

    @GetMapping("/fetch/pharmada/{lastUpdate}")
    public Map<String, Object> fetch(@PathVariable String lastUpdate) {
        Map<String, Object> result = new HashMap<>();

        LocalDateTime lastUpdateDate = LocalDate.parse(lastUpdate).atStartOfDay();
        LocalDate today = LocalDate.now();
        Iterable<Drugstore> drugstores = this.drugstoreRepository.findByCreatedDateGreaterThanEqual(lastUpdateDate);
        Iterable<OnDutyDrugstores> onDutyDrugstores = this.onDutyDrugstoresRepository.findByCreatedDateGreaterThanEqual(lastUpdateDate);

        result.put("cd", DateUtils.formatDateISO(today));
        result.put("version", this.appVersion);
        result.put("drugstores", drugstores);
        result.put("on-duty-drugstores", onDutyDrugstores);

        return result;
    }
}
