package studio.startapps.pandemona.ondutydrugstores.admin;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.ondutydrugstores.exception.OnDutyDrugstoresNotFoundException;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstores;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresDetails;
import studio.startapps.pandemona.ondutydrugstores.internal.OnDutyDrugstoresPreview;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/onduty-drugstores")
public class OnDutyDrugstoreController {

    private static final int PAGE_SIZE = 15;
    private static final String SORT_BY = "endDate";

    private final OnDutyDrugstoresService onDutyDrugstoresService;

    public OnDutyDrugstoreController(OnDutyDrugstoresService onDutyDrugstoresService) {
        this.onDutyDrugstoresService = onDutyDrugstoresService;
    }

    @GetMapping
    public Page<OnDutyDrugstoresPreview> findAll(@PageableDefault(size = PAGE_SIZE, sort = SORT_BY, direction = Sort.Direction.DESC) Pageable pageable) {
        return onDutyDrugstoresService.findAll(pageable);
    }

    @GetMapping(path = "/{onDutyDrugstoresId}")
    public OnDutyDrugstoresDetails get(@PathVariable long onDutyDrugstoresId) throws OnDutyDrugstoresNotFoundException {
        return onDutyDrugstoresService.findById(onDutyDrugstoresId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody OnDutyDrugstoresRequest request) {
        onDutyDrugstoresService.save(request);
    }

    @PutMapping("/{onDutyDrugstoresId}")
    public void update(@PathVariable long onDutyDrugstoresId, @RequestBody OnDutyDrugstoresRequest request) throws OnDutyDrugstoresNotFoundException {
        onDutyDrugstoresService.update(onDutyDrugstoresId, request);
    }

    @DeleteMapping(value = "/{onDutyDrugstoresId}")
    public void delete(@PathVariable long onDutyDrugstoresId) {
        onDutyDrugstoresService.deleteById(onDutyDrugstoresId);
    }

    @GetMapping(path = "/next-period")
    public List<LocalDate> getNextPeriod() {
        return onDutyDrugstoresService.getNextPeriod();
    }
}
