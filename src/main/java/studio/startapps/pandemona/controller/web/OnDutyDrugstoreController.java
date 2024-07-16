package studio.startapps.pandemona.controller.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import studio.startapps.pandemona.exception.drugstore.OnDutyDrugstoresNotFoundException;
import studio.startapps.pandemona.repository.dto.OnDutyDrugstoresDTO;
import studio.startapps.pandemona.repository.entity.OnDutyDrugstores;
import studio.startapps.pandemona.service.OnDutyDrugstoresService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "127.0.0.1:[*]")
@RequestMapping("/api/onduty-drugstores")
public class OnDutyDrugstoreController {

    private static final int PAGE_SIZE = 15;
    private static final String SORT_BY = "endDate";

    private final OnDutyDrugstoresService onDutyDrugstoresService;

    public OnDutyDrugstoreController(OnDutyDrugstoresService onDutyDrugstoresService) {
        this.onDutyDrugstoresService = onDutyDrugstoresService;
    }

    @GetMapping
    public Page<OnDutyDrugstoresDTO> index(@PageableDefault(size = PAGE_SIZE, sort = SORT_BY, direction = Sort.Direction.DESC) Pageable pageable) {
        return onDutyDrugstoresService.findAllAsDTO(pageable);
    }

    @GetMapping(path = "/{onDutyDrugstoresId}")
    public OnDutyDrugstoresDTO get(@PathVariable long onDutyDrugstoresId) throws OnDutyDrugstoresNotFoundException {
        Optional<OnDutyDrugstores> onDutyDrugstoresOptional = onDutyDrugstoresService.findById(onDutyDrugstoresId);
        OnDutyDrugstores onDutyDrugstores = onDutyDrugstoresOptional.orElseThrow(() -> new OnDutyDrugstoresNotFoundException(onDutyDrugstoresId));
        return new OnDutyDrugstoresDTO(onDutyDrugstores);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody OnDutyDrugstores onDutyDrugstores) {
        onDutyDrugstoresService.save(onDutyDrugstores);
    }

    @PutMapping("/{onDutyDrugstoresId}")
    public void update(@PathVariable long onDutyDrugstoresId, @Valid @RequestBody OnDutyDrugstores onDutyDrugstores) {
        onDutyDrugstores.setId(onDutyDrugstoresId);
        onDutyDrugstoresService.save(onDutyDrugstores);
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
